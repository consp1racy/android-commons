package net.xpece.android.dialog.message.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class MessageDialogFragment extends BaseDialogFragment
        implements DialogInterface.OnClickListener {

    private static final String TAG = MessageDialogFragment.class.getSimpleName();

    private DialogFragmentCallbacks callbacks = DialogFragmentCallbacks.DUMMY;

    @CallSuper
    @Override
    public void onAttach(@NonNull final Context context) {
        super.onAttach(context);
        Fragment targetFragment = getTargetFragment();
        if (targetFragment instanceof DialogFragmentCallbacks) {
            callbacks = (DialogFragmentCallbacks) targetFragment;
        } else if (context instanceof DialogFragmentCallbacks) {
            callbacks = (DialogFragmentCallbacks) context;
        } else {
            Log.w(TAG, this + " does not have DialogFragmentCallbacks.");
            callbacks = DialogFragmentCallbacks.DUMMY;
        }
    }

    @CallSuper
    @Override
    public void onDetach() {
        callbacks = DialogFragmentCallbacks.DUMMY;
        super.onDetach();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        final Bundle args = requireArguments();
        final CharSequence title = args.getCharSequence(KEY_TITLE);
        final CharSequence message = args.getCharSequence(KEY_MESSAGE);
        final CharSequence positive = args.getCharSequence(KEY_POSITIVE);
        final CharSequence negative = args.getCharSequence(KEY_NEGATIVE);
        final CharSequence neutral = args.getCharSequence(KEY_NEUTRAL);

        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setPositiveButton(positive, this)
                .setNegativeButton(negative, this)
                .setNeutralButton(neutral, this)
                .setTitle(title)
                .setMessage(message);

        onPrepareDialogBuilder(builder, savedInstanceState);

        return builder.create();
    }

    protected void onPrepareDialogBuilder(@NonNull final AlertDialog.Builder builder,
                                          @Nullable final Bundle savedInstanceState) {
    }

    @Override
    public void onClick(@NonNull final DialogInterface dialog, final int which) {
        callbacks.onClick(this, dialog, which);
    }

    public static final class Builder extends BaseBuilder<Builder, MessageDialogFragment> {

        public Builder(@NonNull Context context) {
            super(context);
        }

        @NonNull
        @Override
        public MessageDialogFragment newDialogFragment() {
            return new MessageDialogFragment();
        }
    }
}
