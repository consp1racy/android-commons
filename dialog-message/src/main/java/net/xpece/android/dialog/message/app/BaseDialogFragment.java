package net.xpece.android.dialog.message.app;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDialogFragment;

public abstract class BaseDialogFragment extends AppCompatDialogFragment {

    protected static final String KEY_MESSAGE = "net.xpece.android.dialog.message.app.BaseDialogFragment.MESSAGE";
    protected static final String KEY_TITLE = "net.xpece.android.dialog.message.app.BaseDialogFragment.TITLE";
    protected static final String KEY_POSITIVE = "net.xpece.android.dialog.message.app.BaseDialogFragment.POSITIVE";
    protected static final String KEY_NEGATIVE = "net.xpece.android.dialog.message.app.BaseDialogFragment.NEGATIVE";
    protected static final String KEY_NEUTRAL = "net.xpece.android.dialog.message.app.BaseDialogFragment.NEUTRAL";

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

        @NonNull protected final Context context;
        @NonNull protected final Bundle args;

        public BaseBuilder(@NonNull Context context) {
            this.context = context;
            args = new Bundle();
        }

        @NonNull
        public B buttonsOkCancel() {
            args.putCharSequence(KEY_POSITIVE, context.getText(android.R.string.ok));
            args.putCharSequence(KEY_NEGATIVE, context.getText(android.R.string.cancel));
            return (B) this;
        }

        @NonNull
        public B buttonsYesNo() {
            args.putCharSequence(KEY_POSITIVE, context.getText(android.R.string.yes));
            args.putCharSequence(KEY_NEGATIVE, context.getText(android.R.string.no));
            return (B) this;
        }

        @NonNull
        public B positiveButtonOk() {
            args.putCharSequence(KEY_POSITIVE, context.getText(android.R.string.ok));
            return (B) this;
        }

        @NonNull
        public B positiveButtonYes() {
            args.putCharSequence(KEY_POSITIVE, context.getText(android.R.string.yes));
            return (B) this;
        }

        @NonNull
        public B negativeButtonCancel() {
            args.putCharSequence(KEY_NEGATIVE, context.getText(android.R.string.cancel));
            return (B) this;
        }

        @NonNull
        public B negativeButtonNo() {
            args.putCharSequence(KEY_NEGATIVE, context.getText(android.R.string.no));
            return (B) this;
        }

        @NonNull
        public B positiveButton(@StringRes int positive) {
            args.putCharSequence(KEY_POSITIVE, context.getText(positive));
            return (B) this;
        }

        @NonNull
        public B positiveButton(CharSequence positive) {
            args.putCharSequence(KEY_POSITIVE, positive);
            return (B) this;
        }

        @NonNull
        public B negativeButton(@StringRes int negative) {
            args.putCharSequence(KEY_NEGATIVE, context.getText(negative));
            return (B) this;
        }

        @NonNull
        public B negativeButton(CharSequence negative) {
            args.putCharSequence(KEY_NEGATIVE, negative);
            return (B) this;
        }

        @NonNull
        public B neutralButton(@StringRes int neutral) {
            args.putCharSequence(KEY_NEUTRAL, context.getText(neutral));
            return (B) this;
        }

        @NonNull
        public B neutralButton(CharSequence neutral) {
            args.putCharSequence(KEY_NEUTRAL, neutral);
            return (B) this;
        }

        @NonNull
        public B message(@StringRes int message) {
            args.putCharSequence(KEY_MESSAGE, context.getText(message));
            return (B) this;
        }

        @NonNull
        public B message(CharSequence message) {
            args.putCharSequence(KEY_MESSAGE, message);
            return (B) this;
        }

        @NonNull
        public B title(@StringRes int title) {
            args.putCharSequence(KEY_TITLE, context.getText(title));
            return (B) this;
        }

        @NonNull
        public B title(CharSequence title) {
            args.putCharSequence(KEY_TITLE, title);
            return (B) this;
        }

        @NonNull
        public B clear() {
            args.clear();
            return (B) this;
        }

        @NonNull
        public F build() {
            Bundle args = new Bundle(this.args);
            F f = newDialogFragment();
            f.setArguments(args);
            return f;
        }

        @NonNull
        public abstract F newDialogFragment();
    }
}
