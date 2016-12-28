package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v7.content.res.AppCompatResources;

/**
 * Created by Eugen on 28.12.2016.
 */

@SuppressWarnings("RestrictedApi")
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class XpAppCompatResources {
    @Nullable
    public static Drawable getDrawable(@NonNull final Context context, @DrawableRes final int resId) {
        try {
            return AppCompatResources.getDrawable(context, resId);
        } catch (NoSuchMethodError ex) {
            return AppCompatDrawableManager.get().getDrawable(context, resId, false);
        }
    }
}
