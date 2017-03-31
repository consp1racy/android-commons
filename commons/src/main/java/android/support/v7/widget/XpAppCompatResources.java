package android.support.v7.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;

/**
 * Created by Eugen on 28.12.2016.
 */

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class XpAppCompatResources {
    @NonNull static DrawableGetter sDrawableGetter = new DrawableGetterDefault();

    @Nullable
    public static Drawable getDrawable(@NonNull final Context context, @DrawableRes final int resId) {
        return sDrawableGetter.getDrawable(context, resId);
    }

    interface DrawableGetter {
        @Nullable
        Drawable getDrawable(@NonNull final Context context, @DrawableRes final int resId);
    }

    static class DrawableGetterDefault implements DrawableGetter {
        @SuppressWarnings("RestrictedApi")
        @Override
        @Nullable
        public Drawable getDrawable(@NonNull final Context context, @DrawableRes final int resId) {
            try {
                final Drawable d = AppCompatResources.getDrawable(context, resId);
                sDrawableGetter = new DrawableGetterAppCompatPublic();
                return d;
            } catch (Throwable ex) {
                try {
                    final Drawable d = AppCompatDrawableManager.get().getDrawable(context, resId, false);
                    sDrawableGetter = new DrawableGetterAppCompatPrivate();
                    return d;
                } catch (Throwable ex2) {
                    try {
                        final Drawable d = ContextCompat.getDrawable(context, resId);
                        sDrawableGetter = new DrawableGetterSupportV4();
                        return d;
                    } catch (Throwable ex3) {
                        if (Build.VERSION.SDK_INT < 21) {
                            sDrawableGetter = new DrawableGetterBase();
                        } else {
                            sDrawableGetter = new DrawableGetterLollipop();
                        }
                        return sDrawableGetter.getDrawable(context, resId);
                    }
                }
            }
        }
    }

    static class DrawableGetterBase implements DrawableGetter {
        @Override
        @Nullable
        @SuppressWarnings("deprecation")
        public Drawable getDrawable(@NonNull final Context context, @DrawableRes final int resId) {
            return context.getResources().getDrawable(resId);
        }
    }

    @RequiresApi(21)
    @TargetApi(21)
    static class DrawableGetterLollipop implements DrawableGetter {
        @Override
        @Nullable
        public Drawable getDrawable(@NonNull final Context context, @DrawableRes final int resId) {
            return context.getDrawable(resId);
        }
    }

    static class DrawableGetterSupportV4 implements DrawableGetter {
        @Override
        @Nullable
        public Drawable getDrawable(@NonNull final Context context, @DrawableRes final int resId) {
            return ContextCompat.getDrawable(context, resId);
        }
    }

    static class DrawableGetterAppCompatPrivate implements DrawableGetter {
        @Override
        @Nullable
        @SuppressWarnings("RestrictedApi")
        public Drawable getDrawable(@NonNull final Context context, @DrawableRes final int resId) {
            return AppCompatDrawableManager.get().getDrawable(context, resId, false);
        }
    }

    static class DrawableGetterAppCompatPublic implements DrawableGetter {
        @Override
        @Nullable
        public Drawable getDrawable(@NonNull final Context context, @DrawableRes final int resId) {
            return AppCompatResources.getDrawable(context, resId);
        }
    }
}
