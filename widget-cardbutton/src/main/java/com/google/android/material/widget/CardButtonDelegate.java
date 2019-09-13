package com.google.android.material.widget;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

import com.google.android.material.shadow.ShadowViewDelegate;

/**
 * Created by Eugen on 03.01.2017.
 */

interface CardButtonDelegate extends ShadowViewDelegate {
    void setForegroundDrawable(@Nullable Drawable foreground);

    boolean getDrawSelectorOnTop();
}
