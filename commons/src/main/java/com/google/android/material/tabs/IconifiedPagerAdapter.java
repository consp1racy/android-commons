package com.google.android.material.tabs;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

/**
 * @author Eugen on 25.02.2017.
 */

public interface IconifiedPagerAdapter {
    @Nullable
    Drawable getPageIcon(int position);

    boolean getDisplayPageTitle(int position);
}
