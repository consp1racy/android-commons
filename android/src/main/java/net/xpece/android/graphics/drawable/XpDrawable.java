package net.xpece.android.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateDecelerateInterpolator;

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
}
