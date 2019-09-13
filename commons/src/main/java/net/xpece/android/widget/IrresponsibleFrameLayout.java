package net.xpece.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.core.view.ViewCompat;

/**
 * FrameLayout that doesn't consume window insets.
 * Ideal as fragment container.
 * Do not use as top level container. Anything extending FrameLayout is ignored.
 */
public class IrresponsibleFrameLayout extends FrameLayout {
    public IrresponsibleFrameLayout(Context context) {
        this(context,null);
    }

    public IrresponsibleFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IrresponsibleFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsEmptyListener());
    }
}
