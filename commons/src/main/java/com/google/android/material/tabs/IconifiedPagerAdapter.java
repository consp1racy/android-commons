package com.google.android.material.tabs;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;


public interface IconifiedPagerAdapter {
    @Nullable
    Drawable getPageIcon(int position);

    boolean getDisplayPageTitle(int position);
}
