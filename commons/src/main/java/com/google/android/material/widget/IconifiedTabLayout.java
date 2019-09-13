package com.google.android.material.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * @deprecated Use {@link com.google.android.material.tabs.IconifiedTabLayout}.
 */
@Deprecated
public class IconifiedTabLayout extends com.google.android.material.tabs.IconifiedTabLayout {

    public IconifiedTabLayout(Context context) {
        super(context);
    }

    public IconifiedTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IconifiedTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
