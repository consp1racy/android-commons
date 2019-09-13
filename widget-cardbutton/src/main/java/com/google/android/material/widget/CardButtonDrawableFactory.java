package com.google.android.material.widget;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.Arrays;

/**
 * Created by Eugen on 03.01.2017.
 */

class CardButtonDrawableFactory {
    private static final float[] TEMP_CORNER_RADII_OUT = new float[8];
    private static final float[] TEMP_CORNER_RADII_IN = new float[8];
    private static final RectF TEMP_RECTF = new RectF();

    private CardButtonDrawableFactory() {
        throw new UnsupportedOperationException();
    }

    private static void fillRectF(@FloatRange(from = 0) final float stroke) {
        TEMP_RECTF.set(stroke, stroke, stroke, stroke);
    }

//    @NonNull
//    static Drawable newGradientDrawableCompat(final float cornerRadius, @ColorInt final int color) {
//        return DrawableCompat.wrap(newGradientDrawable(cornerRadius, color));
//    }

//    @NonNull
//    private static GradientDrawable newGradientDrawable(final float cornerRadius, @ColorInt final int color) {
//        GradientDrawable d = new GradientDrawable();
//        d.setShape(GradientDrawable.RECTANGLE);
//        d.setColor(color);
//        d.setCornerRadius(cornerRadius);
//        return d;
//    }

//    @NonNull
//    private static ShapeDrawable newShapeDrawable(float cornerRadius) {
//        Arrays.fill(TEMP_CORNER_RADII_OUT, cornerRadius);
//        final Shape s = new RoundRectShape(TEMP_CORNER_RADII_OUT, null, null);
//        final ShapeDrawable d = new ShapeDrawable(s);
//        d.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
//        return d;
//    }

    @NonNull
    private static XpRoundRectDrawable newRoundRectDrawable(final float cornerRadius, @ColorInt final int color) {
        return new XpRoundRectDrawable(ColorStateList.valueOf(color), cornerRadius);
    }

    @NonNull
    static Drawable newRoundRectDrawableCompat(final float cornerRadius, @ColorInt final int color) {
        final XpRoundRectDrawable d = newRoundRectDrawable(cornerRadius, color);
        return DrawableCompat.wrap(d);
    }

    @NonNull
    static Drawable newBorderShapeDrawableCompat(@IntRange(from = 0) @Px final int borderWidth, @FloatRange(from = 0) final float cornerRadius) {
        fillRectF(borderWidth);
        Arrays.fill(TEMP_CORNER_RADII_OUT, cornerRadius);
        Arrays.fill(TEMP_CORNER_RADII_IN, Math.max(0, cornerRadius - borderWidth));
        final RoundRectShape s = new RoundRectShape(TEMP_CORNER_RADII_OUT, TEMP_RECTF, TEMP_CORNER_RADII_IN);
        final ShapeDrawable d = new ShapeDrawable(s);
        return DrawableCompat.wrap(d);
    }

    @NonNull
    static Drawable newBorderShapeDrawableCompatForEditMode(@IntRange(from = 0) @Px final int borderWidth, @FloatRange(from = 0) final float cornerRadius) {
        final GradientDrawable d = new GradientDrawable();
        d.setColor(0);
        d.setCornerRadius(cornerRadius);
        d.setStroke(borderWidth, Color.WHITE);
        return d;
    }
}
