package net.xpece.android.app;

import androidx.fragment.app.Fragment;

/**
 * @author Eugen on 15. 9. 2015.
 *
 * @deprecated Don't use fragments in ViewPager. It's a pain.
 */
@Deprecated
public interface OnPrimaryItemChangedListener {
    void onPrimaryItemChanged(Fragment oldFragment, Fragment newFragment);
}
