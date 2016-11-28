package net.xpece.android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Fixed for API 19.
 */

public class SingleLayoutApi19 extends SingleLayout {
    public SingleLayoutApi19(final Context context) {
        super(context);
    }

    public SingleLayoutApi19(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleLayoutApi19(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean fitSystemWindows(final Rect insets) {
        if (Build.VERSION.SDK_INT == 19 || Build.VERSION.SDK_INT == 20) {
            setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            boolean done = false;
            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View child = getChildAt(i);
//                done = child.fitSystemWindows(insets);
                done = XpView.fitSystemWindows(child, insets);
                if (done) {
                    break;
                }
            }
            return done;
        } else {
            return super.fitSystemWindows(insets);
        }
    }
}
