package net.xpece.android.widget;

import android.annotation.TargetApi;
import android.os.Build;
import androidx.annotation.NonNull;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * @author Eugen on 1. 9. 2015.
 */
@TargetApi(16)
public final class XpImageView {

    private static final Field FIELD_ADJUST_VIEW_BOUNDS;

    static {
        if (Build.VERSION.SDK_INT < 16) {
            Field adjustViewBounds = null;
            try {
                adjustViewBounds = ImageView.class.getDeclaredField("mAdjustViewBounds");
                adjustViewBounds.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            FIELD_ADJUST_VIEW_BOUNDS = adjustViewBounds;
        } else {
            FIELD_ADJUST_VIEW_BOUNDS = null;
        }
    }

    private XpImageView() {
        throw new AssertionError("No instances!");
    }

    public static boolean getAdjustViewBounds(@NonNull ImageView imageView) {
        if (Build.VERSION.SDK_INT < 16) {
            try {
                return FIELD_ADJUST_VIEW_BOUNDS.getBoolean(imageView);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return imageView.getAdjustViewBounds();
        }
    }
}
