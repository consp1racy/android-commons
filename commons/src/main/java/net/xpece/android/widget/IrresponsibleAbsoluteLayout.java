package net.xpece.android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IrresponsibleAbsoluteLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @TargetApi(21)
    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        return insets;
    }
}
