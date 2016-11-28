package net.xpece.android.widget;

import android.graphics.drawable.Drawable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by Eugen on 12.11.2015.
 */
public final class XpToolbar {
    private static final Field FIELD_COLLAPSE_ICON;

    private static final Field FIELD_TITLE_TEXT_VIEW;
    private static final Field FIELD_TITLE_TEXT_APPEARANCE;

    private static final Field FIELD_SUBTITLE_TEXT_VIEW;
    private static final Field FIELD_SUBTITLE_TEXT_APPEARANCE;

    static {
        final Class<Toolbar> toolbarClass = Toolbar.class;

        Field f = null;
        try {
            f = toolbarClass.getDeclaredField("mCollapseIcon");
            f.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FIELD_COLLAPSE_ICON = f;

        try {
            f = toolbarClass.getDeclaredField("mTitleTextView");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        FIELD_TITLE_TEXT_VIEW = f;

        f = null;
        try {
            f = toolbarClass.getDeclaredField("mTitleTextAppearance");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        FIELD_TITLE_TEXT_APPEARANCE = f;

        f = null;
        try {
            f = toolbarClass.getDeclaredField("mSubtitleTextView");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        FIELD_SUBTITLE_TEXT_VIEW = f;

        f = null;
        try {
            f = toolbarClass.getDeclaredField("mSubtitleTextAppearance");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        FIELD_SUBTITLE_TEXT_APPEARANCE = f;
    }

    public static Drawable getCollapseIcon(Toolbar toolbar) {
        try {
            return (Drawable) FIELD_COLLAPSE_ICON.get(toolbar);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setCollapseIcon(Toolbar toolbar, Drawable icon) {
        try {
            FIELD_COLLAPSE_ICON.set(toolbar, icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TextView getTitleTextView(Toolbar toolbar) {
        try {
            return (TextView) FIELD_TITLE_TEXT_VIEW.get(toolbar);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @StyleRes
    public static int getTitleTextAppearance(Toolbar toolbar) {
        try {
            return FIELD_TITLE_TEXT_APPEARANCE.getInt(toolbar);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static TextView getSubtitleTextView(Toolbar toolbar) {
        try {
            return (TextView) FIELD_SUBTITLE_TEXT_VIEW.get(toolbar);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @StyleRes
    public static int getSubtitleTextAppearance(Toolbar toolbar) {
        try {
            return FIELD_SUBTITLE_TEXT_APPEARANCE.getInt(toolbar);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
