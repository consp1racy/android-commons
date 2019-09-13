package com.google.android.material.widget;

import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


@RequiresApi(21)
@SuppressWarnings("RestrictedApi")
class AltInsetDrawableApi21 extends AltInsetDrawable {
    protected AltInsetDrawableApi21(@NonNull final Drawable drawable) {
        super(drawable);
    }

    protected AltInsetDrawableApi21(@NonNull final Drawable drawable, final int insetLeft, final int insetTop, final int insetRight, final int insetBottom) {
        super(drawable, insetLeft, insetTop, insetRight, insetBottom);
    }

    @Override
    public void getOutline(@NonNull final Outline outline) {
        getWrappedDrawable().getOutline(outline);
    }
}
