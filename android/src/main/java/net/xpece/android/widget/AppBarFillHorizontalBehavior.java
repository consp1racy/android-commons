package net.xpece.android.widget;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * @author Eugen on 11.09.2016.
 */

public class AppBarFillHorizontalBehavior extends FillHorizontalBehavior {
    public AppBarFillHorizontalBehavior() {
    }

    @Keep
    public AppBarFillHorizontalBehavior(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    AppBarLayout findFirstDependency(final List<View> views) {
        for (View view : views) {
            if (view instanceof AppBarLayout) return (AppBarLayout) view;
        }
        return null;
    }

    @Override
    public boolean layoutDependsOn(final CoordinatorLayout parent, final View child, final View dependency) {
        return dependency instanceof AppBarLayout;
    }
}
