package net.xpece.android.widget;

import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.RelativeLayout;

/**
 * FrameLayout that doesn't consume window insets.
 * Ideal as fragment container.
 * Do not use as top level container. Anything extending FrameLayout is ignored.
 */
public class IrresponsibleRelativeLayout extends RelativeLayout {
    public IrresponsibleRelativeLayout(Context context) {
        super(context);
    }

    public IrresponsibleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IrresponsibleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(21)
    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return insets;
    }
}
