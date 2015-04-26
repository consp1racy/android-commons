package net.xpece.android.content.res;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;

/**
 * Created by Eugen on 20. 3. 2015.
 */
public class XpResources {
    private XpResources() {}

    /**
     * @deprecated Use {@link android.support.v4.content.ContextCompat#getDrawable(Context, int)} instead.
     * @param context
     * @param did
     * @return
     */
    @SuppressWarnings("deprecation")
    @TargetApi(21)
    @Deprecated
    public static Drawable getDrawable(Context context, @DrawableRes int did) {
        if (Build.VERSION.SDK_INT < 21) {
            return context.getResources().getDrawable(did);
        } else {
            return context.getDrawable(did);
        }
    }

    public static boolean resolveBoolean(Context context, @AttrRes int attr, boolean fallback) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        try {
            return ta.getBoolean(0, fallback);
        } finally {
            ta.recycle();
        }
    }

    public static int resolveColor(Context context, @AttrRes int attr, int fallback) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        try {
            return ta.getColor(0, fallback);
        } finally {
            ta.recycle();
        }
    }

    public static ColorStateList resolveColorStateList(Context context, @AttrRes int attr) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        try {
            return ta.getColorStateList(0);
        } finally {
            ta.recycle();
        }
    }

    public static float resolveDimension(Context context, @AttrRes int attr, float fallback) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
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
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        try {
            return ta.getDrawable(0);
        } finally {
            ta.recycle();
        }
    }

    public static String resolveString(Context context, @AttrRes int attr) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        try {
            return ta.getString(0);
        } finally {
            ta.recycle();
        }
    }

    public static CharSequence resolveText(Context context, @AttrRes int attr) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        try {
            return ta.getText(0);
        } finally {
            ta.recycle();
        }
    }

}
