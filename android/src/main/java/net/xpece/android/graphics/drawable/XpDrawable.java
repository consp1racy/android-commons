package net.xpece.android.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.StateSet;
import android.view.animation.AccelerateDecelerateInterpolator;

import net.xpece.android.AndroidUtils;
import net.xpece.android.graphics.XpColorUtils;
import net.xpece.android.graphics.XpTintManager;
import net.xpece.commons.android.R;

/**
 * @author Eugen
 */
public class XpDrawable {

    public static final int MAX_LEVEL = 10000;
    public static final int MIN_LEVEL = 0;

    public static void reverse(final Drawable d, final int duration) {
        animate(d, duration, MAX_LEVEL, MIN_LEVEL);
    }

    public static void animate(final Drawable d, final int duration) {
        animate(d, duration, MIN_LEVEL, MAX_LEVEL);
    }

    @TargetApi(11)
    public static void animate(final Drawable d, final int duration, final int from, final int to) {
        ValueAnimator a = ValueAnimator.ofInt(from, to);
        a.setDuration(duration);
        a.setInterpolator(new AccelerateDecelerateInterpolator());
        a.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                d.setLevel(from);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                d.setLevel(to);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                d.setLevel(to);
            }
        });
        a.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                d.setLevel((Integer) animation.getAnimatedValue());
            }
        });
        a.start();
    }

    @Deprecated
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Drawable getDividerDrawable(Context context, boolean doInset) {
        ColorDrawable color = new ColorDrawable(XpColorUtils.getDividerColor(context));
        if (doInset) {
            boolean rtl = AndroidUtils.isRtl(context);
            int inset = context.getResources().getDimensionPixelOffset(R.dimen.material_content_keyline);
            return new InsetDrawable(color, (rtl ? 0 : inset), 0, (rtl ? inset : 0), 0);
        } else {
            return color;
        }
    }

    @Deprecated
    public static Drawable getDisabledDrawable(Context context, @DrawableRes int did, @ColorRes int cid) {
        Drawable d = ContextCompat.getDrawable(context, did);
        int c = ContextCompat.getColor(context, cid);
        return getDisabledDrawable(d, c);
    }

    @Deprecated
    public static Drawable getDisabledDrawable(Drawable d, int color) {
        byte alpha;
        if (XpColorUtils.isLightColor(color)) {
            alpha = (int) (0.5f + 255 * 0.3f);
        } else {
            alpha = (int) (0.5f + 255 * 0.26f);
        }
        int disabledColor = XpColorUtils.setColorAlpha(color, alpha);

        StateListDrawable sld = new StateListDrawable();
        sld.addState(new int[]{-android.R.attr.state_enabled}, XpTintManager.tintDrawable(d.getConstantState().newDrawable(), disabledColor));
        sld.addState(StateSet.WILD_CARD, XpTintManager.tintDrawable(d.getConstantState().newDrawable(), color));
        return sld;
    }

}
