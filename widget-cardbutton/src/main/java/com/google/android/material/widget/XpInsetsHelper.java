package com.google.android.material.widget;

import android.annotation.SuppressLint;
import android.graphics.Insets;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

import java.lang.reflect.Method;

@SuppressWarnings("JavaReflectionMemberAccess")
@SuppressLint("PrivateApi")
final class XpInsetsHelper {

    private static Method METHOD_GET_OPTICAL_INSETS;

    static {
        try {
            METHOD_GET_OPTICAL_INSETS = Drawable.class.getDeclaredMethod("getOpticalInsets");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    public static Insets getOpticalBounds(final @NonNull Drawable drawable) {
        try {
            return (Insets) METHOD_GET_OPTICAL_INSETS.invoke(drawable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
