package net.xpece.android.graphics;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import net.xpece.android.R;
import net.xpece.android.content.res.XpResources;

/**
 * Created by pechanecjr on 4. 1. 2015.
 */
public class XpTintManager {
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<>();

    public static final int[] DISABLED_STATE_SET = new int[]{-android.R.attr.state_enabled};
    public static final int[] FOCUSED_STATE_SET = new int[]{android.R.attr.state_focused};
    public static final int[] ACTIVATED_STATE_SET = new int[]{android.R.attr.state_activated};
    public static final int[] PRESSED_STATE_SET = new int[]{android.R.attr.state_pressed};
    public static final int[] CHECKED_STATE_SET = new int[]{android.R.attr.state_checked};
    public static final int[] SELECTED_STATE_SET = new int[]{android.R.attr.state_selected};
    public static final int[] NOT_PRESSED_OR_FOCUSED_STATE_SET = new int[]{
        -android.R.attr.state_pressed, -android.R.attr.state_focused};
    public static final int[] EMPTY_STATE_SET = new int[0];

    public static final int[][] DEFAULT_STATE_LIST = new int[][]{
        DISABLED_STATE_SET,
        FOCUSED_STATE_SET,
        ACTIVATED_STATE_SET,
        PRESSED_STATE_SET,
        CHECKED_STATE_SET,
        SELECTED_STATE_SET,
        EMPTY_STATE_SET
    };
    public static final int[][] MENU_STATE_LIST = new int[][]{
        DISABLED_STATE_SET,
        ACTIVATED_STATE_SET,
        CHECKED_STATE_SET,
        EMPTY_STATE_SET
    };
    public static final int[][] DISABLED_STATE_LIST = new int[][]{
        DISABLED_STATE_SET,
        EMPTY_STATE_SET
    };
    private static final float DISABLED_ALPHA = 0.3f;

    private XpTintManager() {
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableId, @ColorRes int colorId) {
        Drawable d = ContextCompat.getDrawable(context, drawableId).mutate();
        ColorStateList c = ContextCompat.getColorStateList(context, colorId);
        return getDrawable(d, c);
    }

    public static Drawable getDrawable(Drawable d, @ColorInt int c) {
        d = DrawableCompat.wrap(d);
        DrawableCompat.setTint(d, c);
        return d;
    }

    public static Drawable getDrawable(Drawable d, ColorStateList c) {
        d = DrawableCompat.wrap(d);
        DrawableCompat.setTintList(d, c);
        return d;
    }

    public static Drawable getControlNormalDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId).mutate();
        int color = XpResources.resolveColor(context, R.attr.colorControlNormal, 0);
        return getDrawable(drawable, color);
    }

    public static Drawable getControlActivatedDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId).mutate();
        int color = XpResources.resolveColor(context, R.attr.colorControlActivated, 0);
        return getDrawable(drawable, color);
    }

    public static Drawable getControlDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId).mutate();
        return getControlDrawable(context, drawable);
    }

    public static Drawable getControlDrawable(Context context, Drawable drawable) {
        ColorStateList csl = getControlColorStateList(context);
        return getDrawable(drawable, csl);
    }

    public static Drawable getDisabledDrawable(Context context, @DrawableRes int drawableId, @ColorRes int colorId) {
        float alpha = XpResources.resolveFloat(context, android.R.attr.disabledAlpha, DISABLED_ALPHA);
        Drawable drawable = ContextCompat.getDrawable(context, drawableId).mutate();
        int color = ContextCompat.getColor(context, colorId);
        return getDisabledDrawable(alpha, drawable, color);
    }

    public static Drawable getDisabledDrawable(float alpha, Drawable drawable, @ColorInt int color) {
        ColorStateList csl = getDisabledColorStateList(alpha, color);
        return getDrawable(drawable, csl);
    }

    public static Drawable getDisabledDrawable(Context context, @DrawableRes int drawableId) {
        float alpha = XpResources.resolveFloat(context, android.R.attr.disabledAlpha, DISABLED_ALPHA);
        Drawable drawable = ContextCompat.getDrawable(context, drawableId).mutate();
        return getDisabledDrawable(alpha, drawable);
    }

    public static Drawable getDisabledDrawable(float alpha, Drawable drawable) {
        Drawable disabled = drawable.getConstantState().newDrawable().mutate();
        disabled.setAlpha((int)(alpha * 255));
        StateListDrawable stateful = new StateListDrawable();
        stateful.addState(DISABLED_STATE_SET, disabled);
        stateful.addState(EMPTY_STATE_SET, drawable);
        return stateful;
    }

    @NonNull
    public static ColorStateList getControlColorStateList(Context context) {
        final int activated = XpResources.resolveColor(context, R.attr.colorControlActivated, Color.BLUE);
        final int normal = XpResources.resolveColor(context, R.attr.colorControlNormal, Color.GRAY);
        final int disabled = getDisabledThemeAttrColor(context, R.attr.colorControlNormal);

        return new ColorStateList(DEFAULT_STATE_LIST, new int[]{
            disabled,
            activated,
            activated,
            activated,
            activated,
            activated,
            normal
        });
    }

    @NonNull
    public static ColorStateList getMenuColorStateList(Context context) {
        final int activated = XpResources.resolveColor(context, R.attr.colorControlActivated, Color.BLUE);
        final int normal = XpResources.resolveColor(context, R.attr.colorControlNormal, Color.GRAY);
        final int disabled = getDisabledThemeAttrColor(context, R.attr.colorControlNormal);

        return new ColorStateList(MENU_STATE_LIST, new int[]{
            disabled,
            activated,
            activated,
            normal
        });
    }

    @NonNull
    public static ColorStateList getDisabledColorStateList(float alpha, @ColorInt int color) {
        final int disabledColor = getDisabledColor(alpha, color);

        return new ColorStateList(DISABLED_STATE_LIST, new int[]{
            disabledColor,
            color
        });
    }

    public static int getDisabledColor(float alpha, @ColorInt int color) {
        final int originalAlpha = Color.alpha(color);
        return ColorUtils.setAlphaComponent(color, Math.round(originalAlpha * alpha));
    }

    private static int getDisabledThemeAttrColor(Context context, int attr) {
        final ColorStateList csl = XpResources.resolveColorStateList(context, attr);
        if (csl != null && csl.isStateful()) {
            // If the CSL is stateful, we'll assume it has a disabled state and use it
            return csl.getColorForState(DISABLED_STATE_SET, csl.getDefaultColor());
        } else {
            // Else, we'll generate the color using disabledAlpha from the theme

            final TypedValue tv = getTypedValue();
            // Now retrieve the disabledAlpha value from the theme
            context.getTheme().resolveAttribute(android.R.attr.disabledAlpha, tv, true);
            final float disabledAlpha = tv.getFloat();

            int color = XpResources.resolveColor(context, attr, 0);
            final int originalAlpha = Color.alpha(color);
            color = ColorUtils.setAlphaComponent(color, Math.round(originalAlpha * disabledAlpha));
            return color;
        }
    }

    public static void tintMenuItem(MenuItem item, ColorStateList color) {
        Drawable icon = item.getIcon();
        if (icon != null) {
            icon = getDrawable(icon.mutate(), color);
            item.setIcon(icon);
        }
    }

    public static void tintMenuItem(MenuItem item, @ColorInt int color) {
        Drawable icon = item.getIcon();
        if (icon != null) {
            icon = getDrawable(icon.mutate(), color);
            item.setIcon(icon);
        }
    }

    public static void tintMenu(Menu menu, ColorStateList color) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            tintMenuItem(item, color);
        }
    }

    public static void tintMenu(Menu menu, @ColorInt int color) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            tintMenuItem(item, color);
        }
    }

    public static void tintMenu(Menu menu, Context context) {
        ColorStateList csl = getMenuColorStateList(context);
        tintMenu(menu, csl);
    }

    public static void tintMenu(Toolbar toolbar) {
        Context context = toolbar.getContext();
        Menu menu = toolbar.getMenu();
        tintMenu(menu, context);
    }

    private static TypedValue getTypedValue() {
        TypedValue typedValue = TL_TYPED_VALUE.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            TL_TYPED_VALUE.set(typedValue);
        }
        return typedValue;
    }

}
