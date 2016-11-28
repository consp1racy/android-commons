package net.xpece.android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.gigamole.library.ShadowLayout;

/**
 * Does not set bottom system inset as its padding.
 * Top aligned toolbar container.
 */

public class OffsetTopAndSidesShadowLayout extends ShadowLayout {
    public OffsetTopAndSidesShadowLayout(final Context context) {
        super(context);
    }

    public OffsetTopAndSidesShadowLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public OffsetTopAndSidesShadowLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean fitSystemWindows(final Rect insets) {
        final boolean value = super.fitSystemWindows(insets);
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), 0);
        return value;
    }
}
