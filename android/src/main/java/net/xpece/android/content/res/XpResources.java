package net.xpece.android.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;

/**
 * Created by Eugen on 20. 3. 2015.
 */
public class XpResources {
    private static final int[] TEMP_ARRAY = new int[1];

    private XpResources() {}

    public static boolean resolveBoolean(Context context, @AttrRes int attr, boolean fallback) {
        TEMP_ARRAY[0] = attr;
        TypedArray ta = context.obtainStyledAttributes(TEMP_ARRAY);
        try {
            return ta.getBoolean(0, fallback);
        } finally {
            ta.recycle();
        }
    }

    public static int resolveColor(Context context, @AttrRes int attr, int fallback) {
        TEMP_ARRAY[0] = attr;
        TypedArray ta = context.obtainStyledAttributes(TEMP_ARRAY);
        try {
            return ta.getColor(0, fallback);
        } finally {
            ta.recycle();
        }
    }

    public static ColorStateList resolveColorStateList(Context context, @AttrRes int attr) {
        TEMP_ARRAY[0] = attr;
        TypedArray ta = context.obtainStyledAttributes(TEMP_ARRAY);
        try {
            return ta.getColorStateList(0);
        } finally {
            ta.recycle();
        }
    }

    public static float resolveDimension(Context context, @AttrRes int attr, float fallback) {
        TEMP_ARRAY[0] = attr;
        TypedArray ta = context.obtainStyledAttributes(TEMP_ARRAY);
        try {
            return ta.getDimension(0, fallback);
        } finally {
            ta.recycle();
        }
    }

    public static int resolveDimensionPixelOffset(Context context, @AttrRes int attr, float fallback) {
        float dimen = resolveDimension(context, attr, fallback);
        return (int) (dimen);
    }

    public static int resolveDimensionPixelSize(Context context, @AttrRes int attr, float fallback) {
        float dimen = resolveDimension(context, attr, fallback);
        return (int) (dimen + 0.5f);
    }

    public static Drawable resolveDrawable(Context context, @AttrRes int attr) {
        TEMP_ARRAY[0] = attr;
        TypedArray ta = context.obtainStyledAttributes(TEMP_ARRAY);
        try {
            return ta.getDrawable(0);
        } finally {
            ta.recycle();
        }
    }

    public static String resolveString(Context context, @AttrRes int attr) {
        TEMP_ARRAY[0] = attr;
        TypedArray ta = context.obtainStyledAttributes(TEMP_ARRAY);
        try {
            return ta.getString(0);
        } finally {
            ta.recycle();
        }
    }

    public static CharSequence resolveText(Context context, @AttrRes int attr) {
        TEMP_ARRAY[0] = attr;
        TypedArray ta = context.obtainStyledAttributes(TEMP_ARRAY);
        try {
            return ta.getText(0);
        } finally {
            ta.recycle();
        }
    }

    public static int resolveResourceId(Context context, @AttrRes int attr, int fallback) {
        TEMP_ARRAY[0] = attr;
        TypedArray ta = context.obtainStyledAttributes(TEMP_ARRAY);
        try {
            return ta.getResourceId(0, fallback);
        } finally {
            ta.recycle();
        }
    }

}
