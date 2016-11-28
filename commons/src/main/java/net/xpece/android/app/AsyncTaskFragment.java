package net.xpece.android.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.WorkerThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.os.AsyncTaskCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.xpece.android.R;

/**
 * Wrapper for an {@link AsyncTask} with optional {@link AppCompatProgressDialog}.
 * <p/>
 * Use {@link #showAsDialog(AsyncTaskFragment, CharSequence)} or {@link #showNoUi(AsyncTaskFragment)}
 * for setting up proper behavior dialog-wise.
 *
 * @author Eugen on 11. 10. 2015.
 */
public abstract class AsyncTaskFragment<Progress, Result> extends BaseDialogFragment {
    private static final String TAG = AsyncTaskFragment.class.getSimpleName();

    public static final String KEY_SHOW_DIALOG = "net.xpece.android.app.AsyncTaskFragment.SHOW_DIALOG";

    boolean mPending;
    Result mPendingResult;
    boolean mPendingCancelled;

    private final Callbacks<Progress, Result> mDefaultCallbacks = new Callbacks<Progress, Result>() {
        @Override
        public void onCancelled(final AsyncTaskFragment<Progress, Result> fragment, final Result result) {
            mPending = true;
            mPendingCancelled = true;
            mPendingResult = result;
        }

        @SafeVarargs
        @Override
        public final void onProgressUpdate(final AsyncTaskFragment<Progress, Result> fragment, final Progress... values) {
        }

        @Override
        public void onPostExecute(final AsyncTaskFragment<Progress, Result> fragment, final Result result) {
            mPending = true;
            mPendingCancelled = false;
            mPendingResult = result;
        }

        @Override
        public void onPreExecute(final AsyncTaskFragment<Progress, Result> fragment) {
        }
    };

    private Callbacks<Progress, Result> mCallbacks = mDefaultCallbacks;

    private final ManagedAsyncTask mTask = new ManagedAsyncTask();

    public static <Progress, Result> void showAsDialog(AsyncTaskFragment<Progress, Result> fragment, Context context) {
        showAsDialog(fragment, R.string.xpc_please_wait, context);
    }

    public static <Progress, Result> void showAsDialog(AsyncTaskFragment<Progress, Result> fragment, @StringRes int dialogMessage, Context context) {
        CharSequence dialogMessage2 = context.getText(dialogMessage);
        showAsDialog(fragment, dialogMessage2);
    }

    public static <Progress, Result> void showAsDialog(AsyncTaskFragment<Progress, Result> fragment, CharSequence dialogMessage) {
        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
            fragment.setArguments(args);
        }
        args.putCharSequence(BaseDialogFragment.KEY_MESSAGE, dialogMessage);
        args.putBoolean(KEY_SHOW_DIALOG, true);
    }

    public static <Progress, Result> void showNoUi(AsyncTaskFragment<Progress, Result> fragment) {
        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
            fragment.setArguments(args);
        }
        args.putBoolean(KEY_SHOW_DIALOG, false);
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        // Show the dialog by default to catch cases when it shouldn't be shown.
        if (args != null) {
            boolean showsDialog = args.getBoolean(KEY_SHOW_DIALOG, true);
            setShowsDialog(showsDialog);
        } else {
            setShowsDialog(true);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        CharSequence title = args.getCharSequence(KEY_TITLE);
        CharSequence message = args.getCharSequence(KEY_MESSAGE);

        AppCompatProgressDialog dialog = new AppCompatProgressDialog(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        // Do not call this function directly from ProgressDialog, it will not work.
        setCancelable(false);

        return dialog;
    }

    @Nullable
    @Override
    public final View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return null;
    }

    @Override
    @CallSuper
    @SuppressWarnings("unchecked")
    public void onStart() {
        super.onStart();

        Fragment targetFragment = getTargetFragment();
        if (targetFragment instanceof Callbacks) {
            mCallbacks = (Callbacks) targetFragment;
        } else {
            Activity activity = getActivity();
            if (activity instanceof Callbacks) {
                mCallbacks = (Callbacks) activity;
            } else {
                throw new IllegalStateException(this + " does not have Callbacks.");
            }
        }

        if (mTask.getStatus() == AsyncTask.Status.PENDING) {
            AsyncTaskCompat.executeParallel(mTask);
        }
    }

    @Override
    public void onStop() {
        mCallbacks = mDefaultCallbacks;

        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();

        executePendingOperations();
    }

    public void execute(final FragmentManager manager, final String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commit();
    }

    /**
     * Request callback action after an activity has been recreated. If the task has completed
     * in the meantime, the result will be delivered
     * via {@link Callbacks#onPostExecute(AsyncTaskFragment, Object)}
     * or {@link Callbacks#onCancelled(AsyncTaskFragment, Object)}. This will do nothing if the task
     * is still running or the result has been delivered.
     * <p/>
     * <s>Warning!</s> The user is responsible for handling results and dismissing the fragment in timely
     * manner (after UI has been restored and when fragment transactions will not cause exceptions).
     */
    private void executePendingOperations() {
        if (mPending) {
            if (mPendingCancelled) {
                mCallbacks.onCancelled(this, mPendingResult);
            } else {
                mCallbacks.onPostExecute(this, mPendingResult);
            }

            mPending = false;
            mPendingCancelled = false;
            mPendingResult = null;
        }
    }

    /**
     * Returns the current status of this task.
     *
     * @return The current status.
     */
    @SuppressWarnings("unused")
    public final AsyncTask.Status getTaskStatus() {
        return mTask.getStatus();
    }

    /**
     * Returns <tt>true</tt> if this task was cancelled before it completed
     * normally. If you are calling {@link #cancelTask(boolean)} on the task,
     * the value returned by this method should be checked periodically from
     * {@link #doInBackground()} to end the task as soon as possible.
     *
     * @return <tt>true</tt> if task was cancelled before it completed
     * @see #cancelTask(boolean)
     */
    @SuppressWarnings("unused")
    public final boolean isTaskCancelled() {
        return mTask.isCancelled();
    }

    /**
     * <p>Attempts to cancel execution of this task.  This attempt will
     * fail if the task has already completed, already been cancelled,
     * or could not be cancelled for some other reason. If successful,
     * and this task has not started when <tt>cancel</tt> is called,
     * this task should never run. If the task has already started,
     * then the <tt>mayInterruptIfRunning</tt> parameter determines
     * whether the thread executing this task should be interrupted in
     * an attempt to stop the task.</p>
     * <p/>
     * <p>Calling this method will result in {@link #onCancelled(Object)} being
     * invoked on the UI thread after {@link #doInBackground()}
     * returns. Calling this method guarantees that {@link #onPostExecute(Object)}
     * is never invoked. After invoking this method, you should check the
     * value returned by {@link #isTaskCancelled()} periodically from
     * {@link #doInBackground()} to finish the task as early as
     * possible.</p>
     *
     * @param mayInterruptIfRunning <tt>true</tt> if the thread executing this
     * task should be interrupted; otherwise, in-progress tasks are allowed
     * to complete.
     * @return <tt>false</tt> if the task could not be cancelled,
     * typically because it has already completed normally;
     * <tt>true</tt> otherwise
     * @see #isTaskCancelled()
     * @see #onCancelled(Object)
     */
    @SuppressWarnings("unused")
    public final boolean cancelTask(boolean mayInterruptIfRunning) {
        return mTask.cancel(mayInterruptIfRunning);
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link AsyncTask#execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @WorkerThread
    protected abstract Result doInBackground();

    /**
     * This method can be invoked from {@link #doInBackground()} to publish updates
     * on the UI thread while the background computation is still running. Each call to this method
     * will trigger the execution of {@link #onProgressUpdate(Object[])} on the UI thread.
     * {@link #onProgressUpdate(Object[])} will not be called if the task has been canceled.
     *
     * @param values The progress values to update the UI with.
     */
    @SafeVarargs
    @WorkerThread
    @SuppressWarnings("unused")
    public final void publishProgress(Progress... values) {
        mTask.triggerPublishProgress(values);
    }

    /**
     * <p>Runs on the UI thread after {@link #cancelTask(boolean)} is invoked and
     * {@link #doInBackground()} has finished.</p>
     *
     * @param result The result, if any, computed in
     * {@link #doInBackground()}, can be null
     * @see #cancelTask(boolean)
     * @see #isTaskCancelled()
     */
    @CallSuper
    @MainThread
    protected void onCancelled(final Result result) {
        mCallbacks.onCancelled(this, result);
    }

    /**
     * Runs on the UI thread after {@link #publishProgress} is invoked.
     * The specified values are the values passed to {@link #publishProgress}.
     *
     * @param values The values indicating progress.
     * @see #publishProgress
     * @see #doInBackground
     */
    @CallSuper
    @MainThread
    @SuppressWarnings("unchecked")
    protected void onProgressUpdate(final Progress... values) {
        mCallbacks.onProgressUpdate(this, values);
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     * <p/>
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param result The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @CallSuper
    @MainThread
    protected void onPostExecute(final Result result) {
        mCallbacks.onPostExecute(this, result);
    }

    /**
     * Runs on the UI thread before {@link #doInBackground}.
     *
     * @see #onPostExecute
     * @see #doInBackground
     */
    @CallSuper
    @MainThread
    protected void onPreExecute() {
        mCallbacks.onPreExecute(this);
    }

    @SuppressWarnings("unused")
    public AppCompatProgressDialog getProgressDialog() {
        return (AppCompatProgressDialog) super.getDialog();
    }

    public interface Callbacks<Progress, Result> {
        void onCancelled(final AsyncTaskFragment<Progress, Result> fragment, final Result result);

        @SuppressWarnings("unchecked")
        void onProgressUpdate(final AsyncTaskFragment<Progress, Result> fragment, final Progress... values);

        void onPostExecute(final AsyncTaskFragment<Progress, Result> fragment, final Result result);

        void onPreExecute(final AsyncTaskFragment<Progress, Result> fragment);
    }

    private class ManagedAsyncTask extends AsyncTask<Void, Progress, Result> {

        @Override
        public final Result doInBackground(final Void[] params) {
            return AsyncTaskFragment.this.doInBackground();
        }

        @Override
        public void onCancelled(final Result result) {
            AsyncTaskFragment.this.onCancelled(result);
        }

        @SafeVarargs
        @Override
        public final void onProgressUpdate(final Progress... values) {
            AsyncTaskFragment.this.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(final Result result) {
            AsyncTaskFragment.this.onPostExecute(result);
        }

        @Override
        public void onPreExecute() {
            AsyncTaskFragment.this.onPreExecute();
        }

        @SafeVarargs
        final void triggerPublishProgress(final Progress... values) {
            publishProgress(values);
        }
    }
}
