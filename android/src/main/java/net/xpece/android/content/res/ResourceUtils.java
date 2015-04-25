package net.xpece.android.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;

/**
 * Created by pechanecjr on 4. 1. 2015.
 * @deprecated Use {@link XpResources} instead.
 */
@Deprecated
public class ResourceUtils {

  private ResourceUtils() {}

  public static boolean getBoolean(Context context, @AttrRes int attr, boolean fallback) {
    return XpResources.resolveBoolean(context, attr, fallback);
  }

  public static int getColor(Context context, @AttrRes int attr, int fallback) {
    return XpResources.resolveColor(context, attr, fallback);
  }

  public static ColorStateList getColorStateList(Context context, @AttrRes int attr) {
    return XpResources.resolveColorStateList(context, attr);
  }

  public static float getDimension(Context context, @AttrRes int attr, float fallback) {
    return XpResources.resolveDimension(context, attr, fallback);
  }

  public static int getDimensionPixelOffset(Context context, @AttrRes int attr, float fallback) {
    return XpResources.resolveDimensionPixelOffset(context, attr, fallback);
  }

  public static int getDimensionPixelSize(Context context, @AttrRes int attr, float fallback) {
    return XpResources.resolveDimensionPixelSize(context, attr, fallback);
  }

  public static Drawable getDrawable(Context context, @AttrRes int attr) {
    return XpResources.resolveDrawable(context, attr);
  }

  public static String getString(Context context, @AttrRes int attr) {
    return XpResources.resolveString(context, attr);
  }

  public static CharSequence getText(Context context, @AttrRes int attr) {
    return XpResources.resolveText(context, attr);
  }

}
