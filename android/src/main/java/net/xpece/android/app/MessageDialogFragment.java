package net.xpece.android.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;import java.lang.CharSequence;import java.lang.IllegalStateException;import java.lang.Override;import java.lang.String;

/**
 * @author Eugen on 31. 12. 2015.
 */
public final class MessageDialogFragment extends BaseDialogFragment
    implements DialogInterface.OnClickListener,
    FragmentCallbacksHelper.ICanOverrideCallbacks<DialogFragmentCallbacks> {
    public static final String TAG = MessageDialogFragment.class.getSimpleName();

    private DialogFragmentCallbacks mCallbacks = DialogFragmentCallbacks.DUMMY;

    @Override
    @CallSuper
    public void onAttach(final Context context) {
        super.onAttach(context);
        if (!FragmentCallbacksHelper.overrideCallbacks(this)) {
            mCallbacks = (DialogFragmentCallbacks) context;
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

        return new AlertDialog.Builder(getContext())
            .setPositiveButton(positive, this)
            .setNegativeButton(negative, this)
            .setNeutralButton(neutral, this)
            .setTitle(title)
            .setMessage(message)
            .create();
    }

    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        mCallbacks.onClick(this, dialog, which);
    }

    @Override
    public void setCallbacks(final DialogFragmentCallbacks callbacks) {
        if (isAdded()) {
            mCallbacks = callbacks;
        } else {
            throw new IllegalStateException("Fragment not added. Cannot override callbacks.");
        }
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
