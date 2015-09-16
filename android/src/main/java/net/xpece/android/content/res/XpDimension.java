package net.xpece.android.content.res;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.DimenRes;
import android.support.v4.util.LruCache;
import android.util.TypedValue;

/**
 * Created by pechanecjr on 4. 1. 2015.
 */
public class XpDimension {

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

    private XpDimension(float dimen) {
        mDimen = dimen;
    }

    private static XpDimension fromAttribute(Context context, @AttrRes int attr) {
        int resId = XpResources.resolveResourceId(context, attr, 0);
        return fromResource(context, resId);
    }

    private static XpDimension fromResource(Context context, @DimenRes int resId) {
        return new XpDimension(context.getResources().getDimension(resId));
    }

    private static XpDimension fromDp(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        XpDimension result = DIMENSION_LRU_CACHE.get(density, dp);
        if (result == null) {
            result = new XpDimension(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
            DIMENSION_LRU_CACHE.put(density, dp, result);
        }
        return result;
    }

    private static XpDimension fromSp(Context context, int sp) {
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        XpDimension result = DIMENSION_LRU_CACHE.get(density, sp);
        if (result == null) {
            result = new XpDimension(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics()));
            DIMENSION_LRU_CACHE.put(density, sp, result);
        }
        return result;
    }

    public static XpDimension fromAttribute(@AttrRes int attr) {
        return fromAttribute(sContext, attr);
    }

    public static XpDimension fromResource(@DimenRes int resId) {
        return fromResource(sContext, resId);
    }

    public static XpDimension fromDp(int dp) {
        return fromDp(sContext, dp);
    }

    public static XpDimension fromSp(int sp) {
        return fromSp(sContext, sp);
    }

    public static XpDimension fromPx(float px) {
        return new XpDimension(px);
    }

    public XpDimension plus(XpDimension that) {
        return new XpDimension(this.mDimen + that.mDimen);
    }

    public XpDimension minus(XpDimension that) {
        return new XpDimension(this.mDimen - that.mDimen);
    }

    public XpDimension plus(float that) {
        return new XpDimension(this.mDimen + that);
    }

    public XpDimension minus(float that) {
        return new XpDimension(this.mDimen - that);
    }

    public XpDimension multiply(float q) {
        return new XpDimension(this.mDimen * q);
    }

    public XpDimension divide(float d) {
        return new XpDimension(this.mDimen / d);
    }

    public float getValue() { return mDimen; }

    public int getPixelSize() { return (int) (mDimen + 0.5f);}

    public int getPixelOffset() { return (int) (mDimen);}

    private static class DimensionLruCache extends LruCache<Integer, XpDimension> {

        public DimensionLruCache(int maxSize) {
            super(maxSize);
        }

        XpDimension get(float density, int request) {
            return get(generateCacheKey(density, request));
        }

        XpDimension put(float density, int request, XpDimension result) {
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
