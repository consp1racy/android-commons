package net.xpece.android.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Eugen on 20. 4. 2015.
 * http://stackoverflow.com/questions/8394681/android-i-am-unable-to-have-viewpager-wrap-content
 */
public class FitChildrenViewPager extends ViewPager {

    public FitChildrenViewPager(Context context) {
        super(context);
    }

    public FitChildrenViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
            getDefaultSize(0, heightMeasureSpec));

        final int measuredWidth = getMeasuredWidth();
        int childWidthSize = measuredWidth - getPaddingLeft() - getPaddingRight();

        int measuredHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            final int widthSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
            final int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

            View child = getChildAt(i);
            child.measure(widthSpec, heightSpec);

            int h = child.getMeasuredHeight();
            if (h > measuredHeight) measuredHeight = h;
        }

        measuredHeight += getPaddingTop();
        measuredHeight += getPaddingBottom();

        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}
