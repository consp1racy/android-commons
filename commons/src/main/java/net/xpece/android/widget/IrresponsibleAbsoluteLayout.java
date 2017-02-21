package net.xpece.android.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.AbsoluteLayout;

/**
 * AbsoluteLayout that doesn't consume window insets.
 * Ideal as fragment container.
 */
@SuppressWarnings("deprecation")
public class IrresponsibleAbsoluteLayout extends AbsoluteLayout {
    public IrresponsibleAbsoluteLayout(Context context) {
        this(context, null);
    }

    public IrresponsibleAbsoluteLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IrresponsibleAbsoluteLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsEmptyListener());
    }
}
