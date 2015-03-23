package net.xpece.android.content.res;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;

/**
 * Created by pechanecjr on 4. 1. 2015.
 */
public class ResourceUtils {

  private ResourceUtils() {}

  public static boolean getBoolean(Context context, @AttrRes int attr, boolean fallback) {
    TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
    try {
      return ta.getBoolean(0, fallback);
    } finally {
      ta.recycle();
    }
  }

  public static int getColor(Context context, @AttrRes int attr, int fallback) {
    TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
    try {
      return ta.getColor(0, fallback);
    } finally {
      ta.recycle();
    }
  }

  public static float getDimension(Context context, @AttrRes int attr, float fallback) {
    TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
    try {
      return ta.getDimension(0, fallback);
    } finally {
      ta.recycle();
    }
  }

  public static int getDimensionPixelOffset(Context context, @AttrRes int attr, float fallback) {
    float dimen = getDimension(context, attr, fallback);
    return (int) (dimen);
  }

  public static int getDimensionPixelSize(Context context, @AttrRes int attr, float fallback) {
    float dimen = getDimension(context, attr, fallback);
    return (int) (dimen + 0.5f);
  }

  public static Drawable getDrawable(Context context, @AttrRes int attr) {
    TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
    try {
      return ta.getDrawable(0);
    } finally {
      ta.recycle();
    }
  }

  public static String getString(Context context, @AttrRes int attr) {
    TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
    try {
      return ta.getString(0);
    } finally {
      ta.recycle();
    }
  }

}
