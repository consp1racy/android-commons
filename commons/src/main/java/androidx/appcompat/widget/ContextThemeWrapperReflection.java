package androidx.appcompat.widget;

import java.lang.reflect.Field;

/**
 * Extracts theme resource ID from a {@link android.view.ContextThemeWrapper}
 * or {@link androidx.appcompat.view.ContextThemeWrapper}.
 * <p>
 * To use this class AppCompat needs to be imported in classpath.
 */
class ContextThemeWrapperReflection {
    private static final Field FWK_THEME_RESOURCE;
    private static final Field ABC_THEME_RESOURCE;

    static {
        Field f = null;
        try {
            f = android.view.ContextThemeWrapper.class.getDeclaredField("mThemeResource");
            f.setAccessible(true);
            FWK_THEME_RESOURCE = f;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        try {
            f = androidx.appcompat.view.ContextThemeWrapper.class.getDeclaredField("mThemeResource");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            // AppCompat not in classpath.
        }
        ABC_THEME_RESOURCE = f;
    }

    static boolean hasAppCompat() {
        return ABC_THEME_RESOURCE != null;
    }

    static boolean isSupportContextThemeWrapper(Object context) {
        return ContextThemeWrapperReflection.hasAppCompat() &&
            context instanceof androidx.appcompat.view.ContextThemeWrapper;
    }

    public static int getThemeResource(final android.view.ContextThemeWrapper context) {
        try {
            return FWK_THEME_RESOURCE.getInt(context);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getThemeResource(final androidx.appcompat.view.ContextThemeWrapper context) {
        try {
            return ABC_THEME_RESOURCE.getInt(context);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getThemeResource(final Object context) {
        if (context instanceof android.view.ContextThemeWrapper) {
            return getThemeResource((android.view.ContextThemeWrapper) context);
        } else if (isSupportContextThemeWrapper(context)) {
            return getThemeResource((androidx.appcompat.view.ContextThemeWrapper) context);
        } else {
            return 0;
        }
    }

    private ContextThemeWrapperReflection() {
        throw new AssertionError();
    }
}
