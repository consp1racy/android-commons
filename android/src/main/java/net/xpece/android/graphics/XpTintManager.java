package net.xpece.android.graphics;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.xpece.android.content.res.XpResources;
import net.xpece.commons.android.R;

/**
 * Created by pechanecjr on 4. 1. 2015.
 */
public class XpTintManager {
    private static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache(6);

    private XpTintManager() {
    }

    public static Drawable tintDrawable(Context context, @DrawableRes int drawableId, @ColorRes int colorId) {
        Drawable d = ContextCompat.getDrawable(context, drawableId).mutate();
        int c = ContextCompat.getColor(context, colorId);
        return tintDrawable(d, c);
    }

    public static Drawable tintDrawable(Drawable d, int c) {
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
            icon = tintDrawable(icon, color);
            item.setIcon(icon);
        }
    }

    public static void tintMenu(Menu menu, int color) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            tintMenuItem(item, color);
        }
    }

    public static void tintMenu(Toolbar toolbar) {
        Context context = toolbar.getContext();
        Menu menu = toolbar.getMenu();
        tintMenu(menu, context);
    }

    @TargetApi(21)
    public static void tintMenu(Menu menu, Context context) {
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            color = XpResources.resolveColor(context, android.R.attr.colorControlNormal, Color.WHITE);
        } else {
            color = XpResources.resolveColor(context, R.attr.colorControlNormal, Color.WHITE);
        }
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
