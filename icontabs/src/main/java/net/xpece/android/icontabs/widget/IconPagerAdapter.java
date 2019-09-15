package net.xpece.android.icontabs.widget;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;


public interface IconPagerAdapter {

    @Nullable
    Drawable getPageIcon(int position);

    boolean getDisplayPageTitle(int position);
}
