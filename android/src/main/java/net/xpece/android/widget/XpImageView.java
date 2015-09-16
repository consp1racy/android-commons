package net.xpece.android.widget;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * @author Eugen on 1. 9. 2015.
 */
@TargetApi(16)
public class XpImageView {

    private static final Field FIELD_GET_ADJUST_VIEW_BOUNDS;

    static {
        if (Build.VERSION.SDK_INT < 16) {
            Field getAdjustViewBounds = null;
            try {
                getAdjustViewBounds = ImageView.class.getDeclaredField("mAdjustViewBounds");
                getAdjustViewBounds.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            FIELD_GET_ADJUST_VIEW_BOUNDS = getAdjustViewBounds;
        } else {
            FIELD_GET_ADJUST_VIEW_BOUNDS = null;
        }
    }

    private XpImageView() {
    }

    public static boolean getAdjustViewBounds(ImageView imageView) {
        if (Build.VERSION.SDK_INT < 16) {
            try {
                return FIELD_GET_ADJUST_VIEW_BOUNDS.getBoolean(imageView);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return imageView.getAdjustViewBounds();
        }
    }
}
