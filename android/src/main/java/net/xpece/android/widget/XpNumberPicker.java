package net.xpece.android.widget;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

/**
 * Created by Eugen on 06.05.2016.
 */
@TargetApi(11)
public final class XpNumberPicker {
    private static final Field FIELD_SELECTION_DIVIDER;

    static {
        Field f = null;
        if (Build.VERSION.SDK_INT >= 11) {
            try {
                f = NumberPicker.class.getDeclaredField("mSelectionDivider");
                f.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
        }
        FIELD_SELECTION_DIVIDER = f;
    }

    private XpNumberPicker() {}

    public static void setSelectionDivider(NumberPicker picker, Drawable divider) {
        if (Build.VERSION.SDK_INT < 11) return;

        Drawable old = getSelectionDivider(picker);
        if (old != divider) {
            if (old != null) {
                old.setCallback(null);
            }
            try {
                FIELD_SELECTION_DIVIDER.set(picker, divider);
                if (divider != null) {
                    divider.setCallback(picker);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        divider.setLayoutDirection(picker.getLayoutDirection());
                    }
                    if (divider.isStateful()) {
                        divider.setState(picker.getDrawableState());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static Drawable getSelectionDivider(NumberPicker picker) {
        if (Build.VERSION.SDK_INT < 11) return null;

        try {
            return (Drawable) FIELD_SELECTION_DIVIDER.get(picker);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setSelectionDividerTint(NumberPicker picker, ColorStateList color) {
        if (Build.VERSION.SDK_INT < 11) return;

        setSelectionDividerTintInt(picker, color);
    }

    static void setSelectionDividerTintInt(final NumberPicker picker, final ColorStateList color) {
        Drawable d = getSelectionDivider(picker);
        if (d != null) {
            d = DrawableCompat.wrap(d);
            DrawableCompat.setTintList(d, color);
            setSelectionDivider(picker, d);
        }
    }
}
