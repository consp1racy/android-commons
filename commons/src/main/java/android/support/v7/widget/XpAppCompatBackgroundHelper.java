package android.support.v7.widget;

import android.view.View;

import java.lang.reflect.Constructor;

@SuppressWarnings("RestrictedApi")
class XpAppCompatBackgroundHelper {
    private static final Constructor<AppCompatBackgroundHelper> CTOR_2420;

    static {
        final Class<AppCompatBackgroundHelper> cls = AppCompatBackgroundHelper.class;
        Constructor<AppCompatBackgroundHelper> ctor2420 = null;
        try {
            ctor2420 = cls.getConstructor(View.class, AppCompatDrawableManager.class);
        } catch (NoSuchMethodException e) {
            //
        }
        CTOR_2420 = ctor2420;
    }

    public static AppCompatBackgroundHelper create(View view) {
        try {
            // Support libraries >= 24.2.0.
            return new AppCompatBackgroundHelper(view);
        } catch (NoSuchMethodError ex) {
            // Support libraries < 24.2.0.
            return createOnSupportLibs2420(view);
        }
    }

    private static AppCompatBackgroundHelper createOnSupportLibs2420(final View view) {
        try {
            return CTOR_2420.newInstance(view, AppCompatDrawableManager.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
