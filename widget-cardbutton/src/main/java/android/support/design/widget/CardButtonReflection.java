package android.support.design.widget;

import android.support.v7.widget.AppCompatButton;

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
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        FIELD_APPCOMPAT_BUTTON_BACKGROUND_HELPER = f;
    }

    static void removeAppCompatBackgroundHelper(final AppCompatButton button) {
        try {
            FIELD_APPCOMPAT_BUTTON_BACKGROUND_HELPER.set(button, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private CardButtonReflection() {
        throw new UnsupportedOperationException();
    }
}
