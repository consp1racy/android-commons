package net.xpece.android.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * FrameLayout that doesn't consume window insets.
 * Ideal as fragment container.
 * Do not use as top level container. Anything extending FrameLayout is ignored.
 */
public class IrresponsibleRelativeLayout extends RelativeLayout {
    public IrresponsibleRelativeLayout(Context context) {
        this(context, null);
    }

    public IrresponsibleRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IrresponsibleRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsEmptyListener());
    }
}
