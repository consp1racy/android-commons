package com.google.android.material.tabs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.viewpager.widget.PagerAdapter;

import net.xpece.android.icontabs.widget.IconPagerAdapter;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class IconTabLayoutImpl extends TabLayout {
    private static final IconPagerAdapter NOOP_ICONIFIED_PAGER_ADAPTER = new IconPagerAdapter() {
        @Nullable
        @Override
        public Drawable getPageIcon(final int position) {
            return null;
        }

        @Override
        public boolean getDisplayPageTitle(final int position) {
            return true;
        }
    };

    private PagerAdapter mPagerAdapter = null;

    public IconTabLayoutImpl(final Context context) {
        super(context);
    }

    public IconTabLayoutImpl(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    public IconTabLayoutImpl(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    void setPagerAdapter(final PagerAdapter pagerAdapter, final boolean addObserver) {
        mPagerAdapter = pagerAdapter;
        super.setPagerAdapter(pagerAdapter, addObserver);
    }

    @Override
    void populateFromPagerAdapter() {
        removeAllTabs();

        if (mPagerAdapter != null) {
            final IconPagerAdapter iconifiedAdapter = getIconifiedPagerAdapter(mPagerAdapter);

            final int adapterCount = mPagerAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                final Tab tab = newTab();
                final CharSequence pageTitle = mPagerAdapter.getPageTitle(i);
                if (iconifiedAdapter.getDisplayPageTitle(i)) {
                    tab.setText(pageTitle);
                } else {
                    tab.setContentDescription(pageTitle);
                }
                tab.setIcon(iconifiedAdapter.getPageIcon(i));
                addTab(tab, false);
            }

            // Make sure we reflect the currently set ViewPager item
            if (viewPager != null && adapterCount > 0) {
                final int curItem = viewPager.getCurrentItem();
                if (curItem != getSelectedTabPosition() && curItem < getTabCount()) {
                    selectTab(getTabAt(curItem));
                }
            }
        }
    }

    private IconPagerAdapter getIconifiedPagerAdapter(final PagerAdapter adapter) {
        if (adapter instanceof IconPagerAdapter) {
            return (IconPagerAdapter) adapter;
        } else {
            return NOOP_ICONIFIED_PAGER_ADAPTER;
        }
    }
}
