/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package net.xpece.android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.TintTypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

/**
 * Provides functionality for DrawerLayout unique to API 21
 */
@RequiresApi(21)
class SingleLayoutCompatApi21 {

    private static final int[] THEME_ATTRS = {
            android.R.attr.colorPrimaryDark
    };

    public static void configureApplyInsets(View drawerLayout) {
        if (drawerLayout instanceof SingleLayoutImpl) {
            drawerLayout.setOnApplyWindowInsetsListener(new InsetsListener());
            drawerLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public static void dispatchChildInsets(View child, Object insets, int gravity) {
        WindowInsets wi = (WindowInsets) insets;
        child.dispatchApplyWindowInsets(wi);
    }

    public static void applyMarginInsets(ViewGroup.MarginLayoutParams lp, Object insets,
            int gravity) {
        WindowInsets wi = (WindowInsets) insets;
        lp.leftMargin = wi.getSystemWindowInsetLeft();
        lp.topMargin = wi.getSystemWindowInsetTop();
        lp.rightMargin = wi.getSystemWindowInsetRight();
        lp.bottomMargin = wi.getSystemWindowInsetBottom();
    }

    public static int getTopInset(Object insets) {
        return insets != null ? ((WindowInsets) insets).getSystemWindowInsetTop() : 0;
    }

    @SuppressWarnings("RestrictedApi")
    public static Drawable getDefaultStatusBarBackground(Context context) {
        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, 0, THEME_ATTRS);
        try {
            return a.getDrawable(0);
        } finally {
            a.recycle();
        }
    }

    static class InsetsListener implements View.OnApplyWindowInsetsListener {
        @Override
        public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
            final SingleLayoutImpl drawerLayout = (SingleLayoutImpl) v;
            drawerLayout.setChildInsets(insets, insets.getSystemWindowInsetTop() > 0);
            return insets.consumeSystemWindowInsets();
        }
    }
}
