package net.xpece.android.app;

import android.support.v4.app.Fragment;

/**
 * @author Eugen on 15. 9. 2015.
 */
public interface OnPrimaryItemChangedListener {
    void onPrimaryItemChanged(Fragment oldFragment, Fragment newFragment);
}
