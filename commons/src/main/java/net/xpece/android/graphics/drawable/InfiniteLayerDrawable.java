package net.xpece.android.graphics.drawable;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * @author Eugen on 28.04.2017.
 */
public class InfiniteLayerDrawable extends LayerDrawableCompat {
    /**
     * Creates a new layer drawable with the list of specified layers.
     *
     * @param layers a list of drawables to use as layers in this new drawable,
     * must be non-null
     */
    public InfiniteLayerDrawable(@NonNull final Drawable[] layers) {
        super(layers);
    }

    @Override
    public int getIntrinsicWidth() {
        return -1;
    }

    @Override
    public int getIntrinsicHeight() {
        return -1;
    }
}
