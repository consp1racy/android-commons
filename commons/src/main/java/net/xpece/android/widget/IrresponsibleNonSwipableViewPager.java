package net.xpece.android.widget;

import android.content.Context;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.core.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class IrresponsibleNonSwipableViewPager extends ViewPager {
    public IrresponsibleNonSwipableViewPager(final Context context) {
        this(context, null);
    }

    public IrresponsibleNonSwipableViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        ViewCompat.setOnApplyWindowInsetsListener(this, new androidx.core.view.OnApplyWindowInsetsListener() {
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
