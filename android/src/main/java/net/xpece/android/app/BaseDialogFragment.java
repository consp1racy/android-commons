package net.xpece.android.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDialogFragment;

/**
 * @author Eugen on 14. 1. 2016.
 */
public abstract class BaseDialogFragment extends AppCompatDialogFragment {

    protected static final String KEY_MESSAGE = "net.xpece.android.app.BaseDialogFragment.MESSAGE";
    protected static final String KEY_TITLE = "net.xpece.android.app.BaseDialogFragment.TITLE";
    protected static final String KEY_POSITIVE = "net.xpece.android.app.BaseDialogFragment.POSITIVE";
    protected static final String KEY_NEGATIVE = "net.xpece.android.app.BaseDialogFragment.NEGATIVE";
    protected static final String KEY_NEUTRAL = "net.xpece.android.app.BaseDialogFragment.NEUTRAL";

    public BaseDialogFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setOnDismissListener(null);
        super.onDestroyView();
    }

    @SuppressWarnings({"unchecked", "unused"})
    public static abstract class BaseBuilder<B extends BaseBuilder, F extends BaseDialogFragment> {
        protected final Context mContext;
        protected final Bundle mArgs;

        public BaseBuilder(Context context) {
            mContext = context;
            mArgs = new Bundle();
        }

        public B positiveButtonOk() {
            mArgs.putCharSequence(KEY_POSITIVE, mContext.getText(android.R.string.ok));
            return (B) this;
        }

        public B positiveButtonYes() {
            mArgs.putCharSequence(KEY_POSITIVE, mContext.getText(android.R.string.yes));
            return (B) this;
        }

        public B negativeButtonCancel() {
            mArgs.putCharSequence(KEY_NEGATIVE, mContext.getText(android.R.string.cancel));
            return (B) this;
        }

        public B negativeButtonNo() {
            mArgs.putCharSequence(KEY_NEGATIVE, mContext.getText(android.R.string.no));
            return (B) this;
        }

        public B positiveButton(@StringRes int positive) {
            mArgs.putCharSequence(KEY_POSITIVE, mContext.getText(positive));
            return (B) this;
        }

        public B positiveButton(CharSequence positive) {
            mArgs.putCharSequence(KEY_POSITIVE, positive);
            return (B) this;
        }

        public B negativeButton(@StringRes int negative) {
            mArgs.putCharSequence(KEY_NEGATIVE, mContext.getText(negative));
            return (B) this;
        }

        public B negativeButton(CharSequence negative) {
            mArgs.putCharSequence(KEY_NEGATIVE, negative);
            return (B) this;
        }

        public B neutralButton(@StringRes int neutral) {
            mArgs.putCharSequence(KEY_NEUTRAL, mContext.getText(neutral));
            return (B) this;
        }

        public B neutralButton(CharSequence neutral) {
            mArgs.putCharSequence(KEY_NEUTRAL, neutral);
            return (B) this;
        }

        public B message(@StringRes int message) {
            mArgs.putCharSequence(KEY_MESSAGE, mContext.getText(message));
            return (B) this;
        }

        public B message(CharSequence message) {
            mArgs.putCharSequence(KEY_MESSAGE, message);
            return (B) this;
        }

        public B title(@StringRes int title) {
            mArgs.putCharSequence(KEY_TITLE, mContext.getText(title));
            return (B) this;
        }

        public B title(CharSequence title) {
            mArgs.putCharSequence(KEY_TITLE, title);
            return (B) this;
        }

        public B clear() {
            mArgs.clear();
            return (B) this;
        }

        public F build() {
            Bundle args = new Bundle(mArgs);
            F f = newDialogFragment();
            f.setArguments(args);
            return f;
        }

        public abstract F newDialogFragment();
    }
}
