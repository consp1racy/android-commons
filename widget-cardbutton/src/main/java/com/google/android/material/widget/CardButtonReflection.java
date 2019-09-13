package com.google.android.material.widget;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import java.lang.reflect.Field;

/**
 * Created by Eugen on 03.01.2017.
 */

class CardButtonReflection {

    private static final Field FIELD_APPCOMPAT_BUTTON_BACKGROUND_HELPER;

    static {
        Field f = null;
        try {
            f = AppCompatButton.class.getDeclaredField("mBackgroundTintHelper");
            f.setAccessible(true);
        } catch (NoSuchFieldException ignored) {
        }
        FIELD_APPCOMPAT_BUTTON_BACKGROUND_HELPER = f;
    }

    static void removeAppCompatBackgroundHelper(@NonNull final AppCompatButton button) {
        try {
            FIELD_APPCOMPAT_BUTTON_BACKGROUND_HELPER.set(button, null);
        } catch (IllegalAccessException ignored) {
        }
    }

    private CardButtonReflection() {
        throw new UnsupportedOperationException();
    }
}
