package android.support.v7.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.Nullable;

/**
 * This exists just so that {@link #getResources()} provides something different than
 * {@link TintResources} or {@link VectorEnabledTintResources}.
 * Then {@link TintContextWrapper#shouldWrap(Context)} returns true.
 */

class XpTintContextWrapper extends ContextWrapper {
    private static final boolean NEEDS_WRAPPING = Build.VERSION.SDK_INT < 21 &&
        ContextThemeWrapperReflection.hasAppCompat();

    private Resources mResources = null;

    /**
     * If I'm not using the activity theme for creating this widget, wrap it again.
     */
    @SuppressLint("RestrictedApi")
    public static Context wrap(final Context context) {
        if (NEEDS_WRAPPING && (context instanceof TintContextWrapper
            || context.getResources() instanceof TintResources
            || context.getResources() instanceof VectorEnabledTintResources)) {
            // Only do work if this really is a candidate for wrapping.
            // This mirrors the condition in TintContextWrapper.

            // This will be the most intermediate Context available, never null.
            // If no other ContextThemeWrappers were used this will be the Activity.
            final ContextWrapper firstContextThemeWrapper = unwrapFirstContextThemeWrapper(context);

            // This will be the *optional* follow up ContextThemeWrapper.
            final ContextWrapper secondContextThemeWrapper = unwrapContextThemeWrapper(firstContextThemeWrapper);

            if (secondContextThemeWrapper != null) {
                final int firstTheme = ContextThemeWrapperReflection.getThemeResource(firstContextThemeWrapper);
                final int secondTheme = ContextThemeWrapperReflection.getThemeResource(secondContextThemeWrapper);
                if (firstTheme != secondTheme) {
                    return new XpTintContextWrapper(context);
                }
            }
        }
        return context;
    }

    @Nullable
    private static ContextWrapper unwrapFirstContextThemeWrapper(final Context context) {
        if (context instanceof android.view.ContextThemeWrapper ||
            ContextThemeWrapperReflection.isSupportContextThemeWrapper(context)) {
            return (ContextWrapper) context;
        } else if (context instanceof ContextWrapper) {
            return unwrapContextThemeWrapper((ContextWrapper) context);
        } else {
            return null;
        }
    }

    @Nullable
    private static ContextWrapper unwrapContextThemeWrapper(final ContextWrapper context) {
        final Context base = context.getBaseContext();
        return unwrapFirstContextThemeWrapper(base);
    }

    public XpTintContextWrapper(final Context base) {
        super(base);
    }

    @Override
    public Resources getResources() {
        if (mResources == null) {
            final Resources res = super.getResources();
            mResources = new ResourcesWrapper(res);
        }
        return mResources;
    }
}
