package android.support.design.widget;

import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/**
 * @author Eugen on 04.04.2017.
 */

@RequiresApi(21)
class AltInsetDrawableApi21 extends AltInsetDrawable {
    protected AltInsetDrawableApi21(final Drawable drawable) {
        super(drawable);
    }

    protected AltInsetDrawableApi21(final Drawable drawable, final int insetLeft, final int insetTop, final int insetRight, final int insetBottom) {
        super(drawable, insetLeft, insetTop, insetRight, insetBottom);
    }

    @Override
    public void getOutline(@NonNull final Outline outline) {
        getWrappedDrawable().getOutline(outline);
    }
}
