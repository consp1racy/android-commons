package net.xpece.android.app;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;import java.lang.Override;

/**
 * @author Eugen on 31. 12. 2015.
 */
public interface DialogFragmentCallbacks {
    DialogFragmentCallbacks DUMMY = new DialogFragmentCallbacks() {
        @Override
        public void onClick(final DialogFragment fragment, final DialogInterface dialog, final int which) {
        }
    };

    void onClick(final DialogFragment fragment, final DialogInterface dialog, final int which);
}
