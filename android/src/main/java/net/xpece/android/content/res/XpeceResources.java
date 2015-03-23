package net.xpece.android.content.res;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;

/**
 * Created by Eugen on 20. 3. 2015.
 */
public class XpeceResources {
    private XpeceResources() {}

    @SuppressWarnings("deprecation")
    @TargetApi(21)
    public static Drawable getDrawable(Context context, @DrawableRes int did) {
        if (Build.VERSION.SDK_INT < 21) {
            return context.getResources().getDrawable(did);
        } else {
            return context.getDrawable(did);
        }
    }
}
