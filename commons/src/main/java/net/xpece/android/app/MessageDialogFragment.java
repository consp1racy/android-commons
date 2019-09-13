package net.xpece.android.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;

/**
 * @author Eugen on 31. 12. 2015.
 */
public class MessageDialogFragment extends BaseDialogFragment
    implements DialogInterface.OnClickListener {
    public static final String TAG = MessageDialogFragment.class.getSimpleName();

    private DialogFragmentCallbacks mCallbacks = DialogFragmentCallbacks.DUMMY;

    @Override
    @CallSuper
    public void onAttach(final Context context) {
        super.onAttach(context);
            Fragment targetFragment = getTargetFragment();
            if (targetFragment instanceof DialogFragmentCallbacks){
                mCallbacks = (DialogFragmentCallbacks) targetFragment;
            } else if (context instanceof DialogFragmentCallbacks) {
                mCallbacks = (DialogFragmentCallbacks) context;
            } else  {
                Log.w(TAG, this + " does not have DialogFragmentCallbacks.");
                mCallbacks = DialogFragmentCallbacks.DUMMY;
            }
    }

    @Override
    @CallSuper
    public void onDetach() {
        mCallbacks = DialogFragmentCallbacks.DUMMY;
        super.onDetach();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        Bundle args = getArguments();
        CharSequence title = args.getCharSequence(KEY_TITLE);
        CharSequence message = args.getCharSequence(KEY_MESSAGE);
        CharSequence positive = args.getCharSequence(KEY_POSITIVE);
        CharSequence negative = args.getCharSequence(KEY_NEGATIVE);
        CharSequence neutral= args.getCharSequence(KEY_NEUTRAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
            .setPositiveButton(positive, this)
            .setNegativeButton(negative, this)
            .setNeutralButton(neutral, this)
            .setTitle(title)
            .setMessage(message);

        onPrepareDialogBuilder(builder, savedInstanceState);

        return builder.create();
    }

    public void onPrepareDialogBuilder(final AlertDialog.Builder builder, final Bundle savedInstanceState) {

    }

    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        mCallbacks.onClick(this, dialog, which);
    }

    public static final class Builder extends BaseBuilder<Builder, MessageDialogFragment> {
        public Builder(Context context) {
            super(context);
        }

        @Override
        public MessageDialogFragment newDialogFragment() {
            return new MessageDialogFragment();
        }
    }
}
