package net.xpece.android.content.res;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.DimenRes;
import android.support.v4.util.LruCache;
import android.util.TypedValue;

/**
 * Created by pechanecjr on 4. 1. 2015.
 */
public class XpDimen {

    private static final DimensionLruCache DIMENSION_LRU_CACHE = new DimensionLruCache(10);

    private static Context sContext = null;

    private final float mDimen;

    /**
     * Call this method in your {@link android.app.Application} class.
     *
     * @param context
     */
    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    private XpDimen(float dimen) {
        mDimen = dimen;
    }

    @Deprecated
    public static XpDimen fromAttribute(Context context, @AttrRes int attr) {
        int resId = XpResources.resolveResourceId(context, attr, 0);
        return fromResource(context, resId);
    }

    @Deprecated
    public static XpDimen fromResource(Context context, @DimenRes int resId) {
        return new XpDimen(context.getResources().getDimension(resId));
    }

    @Deprecated
    public static XpDimen fromDp(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        XpDimen result = DIMENSION_LRU_CACHE.get(density, dp);
        if (result == null) {
            result = new XpDimen(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
            DIMENSION_LRU_CACHE.put(density, dp, result);
        }
        return result;
    }

    @Deprecated
    public static XpDimen fromSp(Context context, int sp) {
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        XpDimen result = DIMENSION_LRU_CACHE.get(density, sp);
        if (result == null) {
            result = new XpDimen(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics()));
            DIMENSION_LRU_CACHE.put(density, sp, result);
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    public static XpDimen fromAttribute(@AttrRes int attr) {
        return fromAttribute(sContext, attr);
    }

    @SuppressWarnings("deprecation")
    public static XpDimen fromResource(@DimenRes int resId) {
        return fromResource(sContext, resId);
    }

    @SuppressWarnings("deprecation")
    public static XpDimen fromDp(int dp) {
        return fromDp(sContext, dp);
    }

    @SuppressWarnings("deprecation")
    public static XpDimen fromSp(int sp) {
        return fromSp(sContext, sp);
    }

    public static XpDimen fromPx(float px) {
        return new XpDimen(px);
    }

    public XpDimen plus(XpDimen that) {
        return new XpDimen(this.mDimen + that.mDimen);
    }

    public XpDimen minus(XpDimen that) {
        return new XpDimen(this.mDimen - that.mDimen);
    }

    public XpDimen plus(float that) {
        return new XpDimen(this.mDimen + that);
    }

    public XpDimen minus(float that) {
        return new XpDimen(this.mDimen - that);
    }

    public XpDimen multiply(float q) {
        return new XpDimen(this.mDimen * q);
    }

    public XpDimen divide(float d) {
        return new XpDimen(this.mDimen / d);
    }

    public float getValue() { return mDimen; }

    public int getPixelSize() { return (int) (mDimen + 0.5f);}

    public int getPixelOffset() { return (int) (mDimen);}

    private static class DimensionLruCache extends LruCache<Integer, XpDimen> {

        public DimensionLruCache(int maxSize) {
            super(maxSize);
        }

        XpDimen get(float density, int request) {
            return get(generateCacheKey(density, request));
        }

        XpDimen put(float density, int request, XpDimen result) {
            return put(generateCacheKey(density, request), result);
        }

        private static int generateCacheKey(float density, int request) {
            int hashCode = 1;
            hashCode = 31 * hashCode + Float.floatToIntBits(density);
            hashCode = 31 * hashCode + request;
            return hashCode;
        }
    }
}
