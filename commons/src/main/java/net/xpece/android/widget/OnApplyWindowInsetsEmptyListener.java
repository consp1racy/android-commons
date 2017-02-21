package net.xpece.android.widget;

import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;

/**
 * @author Eugen on 22.02.2017.
 */

public class OnApplyWindowInsetsEmptyListener implements OnApplyWindowInsetsListener {
    @Override
    public WindowInsetsCompat onApplyWindowInsets(final View v, final WindowInsetsCompat insets) {
        return insets;
    }
}
