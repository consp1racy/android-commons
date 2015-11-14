package net.xpece.android.widget;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;

import java.lang.reflect.Field;

/**
 * Created by Eugen on 12.11.2015.
 */
public class XpToolbar {
    private static final Field FIELD_COLLAPSE_ICON;

    static {
        Field collapseIcon = null;
        try {
            collapseIcon = Toolbar.class.getDeclaredField("mCollapseIcon");
            collapseIcon.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FIELD_COLLAPSE_ICON = collapseIcon;
    }

    public static void setCollapseIcon(Toolbar toolbar, Drawable icon) {
        try {
            FIELD_COLLAPSE_ICON.set(toolbar, icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
