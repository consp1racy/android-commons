package net.xpece.commons.android.graphics;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;

import net.xpece.commons.android.R;
import net.xpece.commons.android.content.AndroidUtils;

/**
 * Created by pechanecjr on 4. 1. 2015.
 */
public class TintUtils {
  private static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache(6);

  private TintUtils() {
  }

  public static Drawable getDrawableWithColorControlNormal(Context context, @DrawableRes int drawableId) {
    Drawable d = context.getApplicationContext().getResources().getDrawable(drawableId).mutate();
    return getDrawableWithColorControlNormal(context, d);
  }

  @TargetApi(21)
  public static Drawable getDrawableWithColorControlNormal(Context context, Drawable d) {
    TypedArray ta = context.obtainStyledAttributes(new int[]{AndroidUtils.API_21 ? android.R.attr.colorControlNormal : R.attr.colorControlNormal});
    int c = ta.getColor(0, Color.BLACK);
    ta.recycle();
    return getDrawable(d, c);
  }

  public static Drawable getDrawable(Context context, @DrawableRes int drawableId, @ColorRes int colorId) {
    Drawable d = context.getResources().getDrawable(drawableId).mutate();
    int c = context.getResources().getColor(colorId);
    return getDrawable(d, c);
  }

  public static Drawable getDrawable(Drawable d, int c) {
    PorterDuffColorFilter cf = COLOR_FILTER_CACHE.get(c);
    if (cf == null) {
      cf = new PorterDuffColorFilter(c, PorterDuff.Mode.SRC_IN);
      COLOR_FILTER_CACHE.put(c, cf);
    }
    d.setColorFilter(cf);
    return d;
  }

  public static void tintMenuItem(MenuItem item, int color) {
    Drawable icon = item.getIcon();
    if (icon != null) {
      icon = TintUtils.getDrawable(icon, color);
      item.setIcon(icon);
    }
  }

  public static void tintMenu(Menu menu, int color) {
    for (int i = 0; i < menu.size(); i++) {
      MenuItem item = menu.getItem(i);
      tintMenuItem(item, color);
    }
  }

  @TargetApi(21)
  public static void tintMenuWithColorControlNormal(Menu menu, Context context) {
    TypedArray ta = context.obtainStyledAttributes(new int[]{AndroidUtils.API_21 ? android.R.attr.colorControlNormal : R.attr.colorControlNormal});
    int color = ta.getColor(0, Color.BLACK);
    ta.recycle();
    tintMenu(menu, color);
  }

  private static class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {

    public ColorFilterLruCache(int maxSize) {
      super(maxSize);
    }

    PorterDuffColorFilter get(int color, PorterDuff.Mode mode) {
      return get(generateCacheKey(color, mode));
    }

    PorterDuffColorFilter put(int color, PorterDuff.Mode mode, PorterDuffColorFilter filter) {
      return put(generateCacheKey(color, mode), filter);
    }

    private static int generateCacheKey(int color, PorterDuff.Mode mode) {
      int hashCode = 1;
      hashCode = 31 * hashCode + color;
      hashCode = 31 * hashCode + mode.hashCode();
      return hashCode;
    }
  }
}
