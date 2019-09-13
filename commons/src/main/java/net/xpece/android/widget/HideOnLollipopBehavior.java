package net.xpece.android.widget;

import android.content.Context;
import android.os.Build;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * @deprecated Don't even create a widget that's not going to be used.
 */
@Deprecated
public class HideOnLollipopBehavior extends CoordinatorLayout.Behavior<View> {
    public HideOnLollipopBehavior() {
    }

    public HideOnLollipopBehavior(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(final CoordinatorLayout parent, final View child, final int parentWidthMeasureSpec, final int widthUsed, final int parentHeightMeasureSpec, final int heightUsed) {
        if (Build.VERSION.SDK_INT >= 21) {
            child.setVisibility(View.GONE);
        }
        return false;
    }
}
