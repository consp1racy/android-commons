package net.xpece.android.dialog.message.app;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public interface DialogFragmentCallbacks {
    DialogFragmentCallbacks DUMMY = new DialogFragmentCallbacks() {
        @Override
        public void onClick(@NonNull final DialogFragment fragment, @NonNull final DialogInterface dialog, final int which) {
        }
    };

    void onClick(@NonNull final DialogFragment fragment, @NonNull final DialogInterface dialog, final int which);
}
