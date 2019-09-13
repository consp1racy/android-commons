package net.xpece.android.widget;

import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;
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
