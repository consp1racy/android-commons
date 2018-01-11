package net.xpece.android.view;

import android.graphics.Rect;
import android.view.View;

import java.lang.reflect.Method;

/**
 * @author Eugen on 18.08.2016.
 * @hide
 */

final class XpViewReflect {
    private static final Method METHOD_FIT_SYSTEM_WINDOWS;

    static {
        Method fitSystemWindows = null;
        try {
            fitSystemWindows = View.class.getDeclaredMethod("fitSystemWindows", Rect.class);
            fitSystemWindows.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        METHOD_FIT_SYSTEM_WINDOWS = fitSystemWindows;
    }

    private XpViewReflect() {
        throw new AssertionError("No instances!");
    }

    @Deprecated
    public static boolean fitSystemWindows(View view, Rect insets) {
        try {
            return (boolean) METHOD_FIT_SYSTEM_WINDOWS.invoke(view, insets);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
