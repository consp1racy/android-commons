package net.xpece.android.graphics;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.util.LruCache;

/**
 * Created by Eugen on 14.11.2015.
 */
public class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {

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
