package net.xpece.android.graphics.drawable;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.Px;


public class EmptyDrawable extends ColorDrawable {
    private final int widthPx;
    private final int heightPx;

    public EmptyDrawable(@Px int sizePx) {
        this(sizePx, sizePx);
    }

    public EmptyDrawable(@Px int widthPx, @Px int heightPx) {
        super(Color.TRANSPARENT);
        this.widthPx = widthPx;
        this.heightPx = heightPx;
    }

    @Override
    public int getIntrinsicWidth() {
        return widthPx;
    }

    @Override
    public int getIntrinsicHeight() {
        return heightPx;
    }
}