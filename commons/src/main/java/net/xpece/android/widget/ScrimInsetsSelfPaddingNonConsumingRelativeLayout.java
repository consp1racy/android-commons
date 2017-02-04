package net.xpece.android.widget;

import android.content.Context;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;

/**
 * @author Eugen on 19.12.2016.
 */

public class ScrimInsetsSelfPaddingNonConsumingRelativeLayout
    extends ScrimInsetsNonConsumingRelativeLayout {
    public ScrimInsetsSelfPaddingNonConsumingRelativeLayout(final Context context) {
        super(context);
    }

    public ScrimInsetsSelfPaddingNonConsumingRelativeLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrimInsetsSelfPaddingNonConsumingRelativeLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInsetsChanged(final WindowInsetsCompat insets) {
        super.onInsetsChanged(insets);
        setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
    }
}
