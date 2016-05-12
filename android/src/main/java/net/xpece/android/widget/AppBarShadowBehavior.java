package net.xpece.android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Eugen on 19. 4. 2016.
 */
@TargetApi(11)
public class AppBarShadowBehavior extends CoordinatorLayout.Behavior<View> {

    private boolean LOLLIPOP = Build.VERSION.SDK_INT >= 21;

    public AppBarShadowBehavior() {
    }

    public AppBarShadowBehavior(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(final CoordinatorLayout parent, final View child, final int layoutDirection) {
        if (LOLLIPOP) {
            child.setVisibility(View.GONE);
        }
        return false;
    }

    @Override
    public boolean layoutDependsOn(final CoordinatorLayout parent, final View child, final View dependency) {
        return dependency instanceof AppBarLayout && !LOLLIPOP;
    }

    @Override
    public boolean onDependentViewChanged(final CoordinatorLayout parent, final View child, final View dependency) {
        ViewGroup.MarginLayoutParams dlp = (ViewGroup.MarginLayoutParams) dependency.getLayoutParams();
        final int i = dependency.getHeight() + dlp.topMargin + dlp.bottomMargin;
        ViewCompat.setTranslationY(child, i);
        return true;
    }

    @Override
    public void onDependentViewRemoved(final CoordinatorLayout parent, final View child, final View dependency) {
        ViewCompat.setTranslationY(child, 0);
    }
}
