package net.xpece.android.widget;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

public final class XpNumberPicker {
    private static final Field FIELD_SELECTION_DIVIDER;

    static {
        Field f = null;
            try {
                f = NumberPicker.class.getDeclaredField("mSelectionDivider");
                f.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
        FIELD_SELECTION_DIVIDER = f;
    }

    private XpNumberPicker() {}

    public static void setSelectionDivider(@NonNull NumberPicker picker, @Nullable Drawable divider) {
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

    @Nullable
    public static Drawable getSelectionDivider(@NonNull NumberPicker picker) {
        try {
            return (Drawable) FIELD_SELECTION_DIVIDER.get(picker);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setSelectionDividerTint(@NonNull NumberPicker picker, @Nullable ColorStateList color) {
        Drawable d = getSelectionDivider(picker);
        if (d != null) {
            d = DrawableCompat.wrap(d);
            DrawableCompat.setTintList(d, color);
            setSelectionDivider(picker, d);
        }
    }
}
