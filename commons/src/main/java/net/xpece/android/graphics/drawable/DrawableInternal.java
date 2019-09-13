package net.xpece.android.graphics.drawable;

import android.content.res.Resources;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;

/**
 * Created by Eugen on 26.11.2016.
 */

class DrawableInternal {

    /**
     * Scales a floating-point pixel value from the source density to the
     * target density.
     *
     * @param pixels the pixel value for use in source density
     * @param sourceDensity the source density
     * @param targetDensity the target density
     * @return the scaled pixel value for use in target density
     */
    public static float scaleFromDensity(float pixels, int sourceDensity, int targetDensity) {
        return pixels * targetDensity / sourceDensity;
    }

    /**
     * Scales a pixel value from the source density to the target density,
     * optionally handling the resulting pixel value as a size rather than an
     * offset.
     * <p>
     * A size conversion involves rounding the base value and ensuring that
     * a non-zero base value is at least one pixel in size.
     * <p>
     * An offset conversion involves simply truncating the base value to an
     * integer.
     *
     * @param pixels the pixel value for use in source density
     * @param sourceDensity the source density
     * @param targetDensity the target density
     * @param isSize {@code true} to handle the resulting scaled value as a
     *               size, or {@code false} to handle it as an offset
     * @return the scaled pixel value for use in target density
     */
    static int scaleFromDensity(
        int pixels, int sourceDensity, int targetDensity, boolean isSize) {
        if (pixels == 0 || sourceDensity == targetDensity) {
            return pixels;
        }

        final float result = pixels * targetDensity / (float) sourceDensity;
        if (!isSize) {
            return (int) result;
        }

        final int rounded = Math.round(result);
        if (rounded != 0) {
            return rounded;
        } else if (pixels > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    static int resolveDensity(@Nullable Resources r, int parentDensity) {
        final int densityDpi = r == null ? parentDensity : r.getDisplayMetrics().densityDpi;
        return densityDpi == 0 ? DisplayMetrics.DENSITY_DEFAULT : densityDpi;
    }

}
