package net.xpece.android.widget;

import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;


public class OnApplyWindowInsetsEmptyListener implements OnApplyWindowInsetsListener {
    @Override
    public WindowInsetsCompat onApplyWindowInsets(final View v, final WindowInsetsCompat insets) {
        return insets;
    }
}
