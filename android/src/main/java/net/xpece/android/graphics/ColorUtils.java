package net.xpece.android.graphics;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.StateSet;

import net.xpece.android.content.res.ResourceUtils;

/**
 * Created by pechanecjr on 4. 1. 2015.
 */
public class ColorUtils {

    public static final int COLOR_TEXT_PRIMARY_DARK = Color.parseColor("#ffffffff"); // 100% white
    public static final int COLOR_TEXT_PRIMARY_LIGHT = Color.parseColor("#de000000"); // 87% black

    /**
     * Compute text color based on background luminance.
     * <p/>
     * Sauce: http://stackoverflow.com/questions/1855884/determine-font-color-based-on-background-color
     * More sauce: http://stackoverflow.com/a/596243/2444099
     *
     * @param background
     * @return 100% white or 87% black.
     */
    public static int getTextColor(int background) {
        if (isLightColor(background)) {
            return COLOR_TEXT_PRIMARY_LIGHT;
        } else {
            return COLOR_TEXT_PRIMARY_DARK;
        }
    }

    public static boolean isLightColor(int background) {
        double a = getLuminance(background);
        // it was so close...
        return (int) (a * 100) > 50;
    }

    public static int getScrimColor(int background) {
        double a = getLuminance(background);
        if ((int) (a * 100) <= 50) // it was so close...
            return Color.BLACK;
        else
            return Color.WHITE;
    }

    public static double getLuminance(int color) {
        int r = (color & 0xff0000) >> 16;
        int g = (color & 0xff00) >> 8;
        int b = color & 0xff;
        return getLuminance(r, g, b);
    }

    private static double getLuminance(int r, int g, int b) {
        // Counting the perceptive luminance - human eye favors green color...
        return (0.299 * r + 0.587 * g + 0.114 * b) / 255;
    }

//  private static double getLuminance(int r, int g, int b) {
//    return (r / 3.0 + g / 3.0 + b / 3.0) / 255;
//  }

//  private static double getLuminance(int r, int g, int b) {
//    return (0.2126 * r + 0.7152 * g + 0.0722 * b) / 255;
//  }

    public static int setColorLightness(int color, float light) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= Math.min(light, 1f); // value component
        return Color.HSVToColor(hsv);
    }

    /**
     * Set the alpha value of the {@code color} to be the given {@code alpha} value.
     */
    public static int setColorAlpha(int color, byte alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    /**
     * Blend {@code color1} and {@code color2} using the given ratio.
     *
     * @param ratio of which to blend. 1.0 will return {@code color1}, 0.5 will give an even blend,
     * 0.0 will return {@code color2}.
     */
    public static int blendColors(int color1, int color2, float ratio) {
        final float inverseRatio = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRatio);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRatio);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRatio);
        return Color.rgb((int) r, (int) g, (int) b);
    }

    public static int blendColorsAlpha(int color1, int color2, float ratio) {
        final float inverseRatio = 1f - ratio;
        float a = (Color.alpha(color1) * ratio) + (Color.alpha(color2) * inverseRatio);
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRatio);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRatio);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRatio);
        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

    /**
     * {@link android.R.attr#colorForeground} with 12% alpha.
     *
     * @param context
     * @return
     */
    public static int getDividerColor(Context context) {
        return ResourceUtils.getColor(context, android.R.attr.colorForeground, 0) & 0x1effffff;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static ColorStateList createActivatedColor(int passive, int active) {
        return new ColorStateList(
            new int[][]{new int[]{android.R.attr.state_activated}, StateSet.WILD_CARD},
            new int[]{active, passive});
    }

    public static float interpolate(float min, float max, float value) {
        if (max < min) {
            throw new IllegalArgumentException("max cannot be less than min.");
//            float temp = min;
//            min = max;
//            max = temp;
        }
        value = Math.min(max, Math.min(max, value));
        float range = Math.abs(max - min);
        float pos = Math.abs(value - min);
        return pos / range;
    }
}
