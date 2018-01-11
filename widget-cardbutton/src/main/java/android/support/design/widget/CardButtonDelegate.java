package android.support.design.widget;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

/**
 * Created by Eugen on 03.01.2017.
 */

interface CardButtonDelegate extends ShadowViewDelegate {
    void setForegroundDrawable(@Nullable Drawable foreground);

    boolean getDrawSelectorOnTop();
}
