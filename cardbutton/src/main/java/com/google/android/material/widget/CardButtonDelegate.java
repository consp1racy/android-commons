package com.google.android.material.widget;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

import com.google.android.material.shadow.ShadowViewDelegate;


interface CardButtonDelegate extends ShadowViewDelegate {
    void setForegroundDrawable(@Nullable Drawable foreground);

    boolean getDrawSelectorOnTop();
}
