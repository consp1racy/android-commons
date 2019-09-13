package net.xpece.android.app;

import androidx.fragment.app.Fragment;

/**
 * @deprecated Don't use fragments in ViewPager. It's a pain.
 */
@Deprecated
public interface OnPrimaryItemChangedListener {
    void onPrimaryItemChanged(Fragment oldFragment, Fragment newFragment);
}
