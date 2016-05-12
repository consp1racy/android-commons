package net.xpece.android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.internal.ScrimInsetsFrameLayout;
import android.util.AttributeSet;

/**
 * Created by Eugen on 29.03.2016.
 */
public class PadScrimInsetsFrameLayout extends ScrimInsetsFrameLayout {

    public PadScrimInsetsFrameLayout(Context context) {
        super(context);
    }

    public PadScrimInsetsFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PadScrimInsetsFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInsetsChanged(Rect insets) {
        setPadding(insets.left, insets.top, insets.right, insets.bottom);
    }

}
