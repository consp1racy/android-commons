package android.support.design.widget;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

/**
 * @author Eugen on 25.02.2017.
 */

public interface IconifiedPagerAdapter {
    IconifiedPagerAdapter NOOP = new IconifiedPagerAdapter() {
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

    @Nullable
    Drawable getPageIcon(int position);

    boolean getDisplayPageTitle(int position);
}
