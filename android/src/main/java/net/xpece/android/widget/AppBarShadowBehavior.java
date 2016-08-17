package net.xpece.android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Keep;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Emulates AppBarLayout shadow on platforms older than Lollipop.
 *
 * @author Eugen on 19. 4. 2016.
 */
public class AppBarShadowBehavior extends CoordinatorLayout.Behavior<View> {

    private static final AppBarShadowBehaviorImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new AppBarShadowBehaviorImplDisabled();
        } else if (Build.VERSION.SDK_INT >= 11) {
            IMPL = new AppBarShadowBehaviorImplEnabled();
        } else {
            IMPL = new AppBarShadowBehaviorImplDisabled();
        }
    }

    public AppBarShadowBehavior() {
    }

    @Keep
    public AppBarShadowBehavior(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(final CoordinatorLayout parent, final View child, final int layoutDirection) {
        IMPL.onLayoutChild(child);
        return false;
    }

    @Override
    public boolean layoutDependsOn(final CoordinatorLayout parent, final View child, final View dependency) {
        return IMPL.layoutDependsOn(dependency);
    }

    @Override
    @TargetApi(11)
    public boolean onDependentViewChanged(final CoordinatorLayout parent, final View child, final View dependency) {
        if (dependency.getVisibility() == View.VISIBLE) {
            ViewGroup.MarginLayoutParams dlp = (ViewGroup.MarginLayoutParams) dependency.getLayoutParams();
            final int i = dependency.getHeight() + dlp.topMargin + dlp.bottomMargin;
            ViewCompat.setTranslationY(child, i);
            child.setVisibility(View.VISIBLE);
        } else {
            child.setVisibility(View.GONE);
        }
        return true;
    }

    @Override
    @TargetApi(11)
    public void onDependentViewRemoved(final CoordinatorLayout parent, final View child, final View dependency) {
        ViewCompat.setTranslationY(child, 0);
        child.setVisibility(View.GONE);
    }

    interface AppBarShadowBehaviorImpl {
        void onLayoutChild(View child);
        boolean layoutDependsOn(View dependency);
    }

    static class AppBarShadowBehaviorImplDisabled implements AppBarShadowBehaviorImpl {

        @Override
        public void onLayoutChild(View child) {
            child.setVisibility(View.GONE);
        }

        @Override
        public boolean layoutDependsOn(View dependency) {
            return false;
        }
    }

    static class AppBarShadowBehaviorImplEnabled implements AppBarShadowBehaviorImpl {

        @Override
        public void onLayoutChild(View child) {
            // No-op.
        }

        @Override
        public boolean layoutDependsOn(View dependency) {
            return dependency instanceof AppBarLayout;
        }
    }
}
