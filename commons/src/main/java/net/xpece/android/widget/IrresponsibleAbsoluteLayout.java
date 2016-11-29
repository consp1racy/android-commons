package net.xpece.android.widget;

import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.AbsoluteLayout;

/**
 * AbsoluteLayout that doesn't consume window insets.
 * Ideal as fragment container.
 */
@SuppressWarnings("deprecation")
public class IrresponsibleAbsoluteLayout extends AbsoluteLayout {
    public IrresponsibleAbsoluteLayout(Context context) {
        super(context);
    }

    public IrresponsibleAbsoluteLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IrresponsibleAbsoluteLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(21)
    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return insets;
    }
}
