package net.xpece.android.app;

import android.content.DialogInterface;
import androidx.fragment.app.DialogFragment;import java.lang.Override;

public interface DialogFragmentCallbacks {
    DialogFragmentCallbacks DUMMY = new DialogFragmentCallbacks() {
        @Override
        public void onClick(final DialogFragment fragment, final DialogInterface dialog, final int which) {
        }
    };

    void onClick(final DialogFragment fragment, final DialogInterface dialog, final int which);
}
