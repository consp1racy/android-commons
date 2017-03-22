package net.xpece.android.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Eugen on 25.02.2017.
 */

public class IrresponsibleNonSwipableViewPager extends ViewPager {
    public IrresponsibleNonSwipableViewPager(final Context context) {
        this(context, null);
    }

    public IrresponsibleNonSwipableViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        ViewCompat.setOnApplyWindowInsetsListener(this, new android.support.v4.view.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(final View v, final WindowInsetsCompat insets) {
                return insets;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        return false;
    }
}
