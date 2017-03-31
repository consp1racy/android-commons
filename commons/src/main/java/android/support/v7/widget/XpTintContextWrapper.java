package android.support.v7.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.StyleRes;
import android.support.v7.view.ContextThemeWrapper;

import net.xpece.android.content.XpContext;

import java.util.WeakHashMap;

/**
 * @author Eugen on 05.08.2016.
 */

@SuppressWarnings("RestrictedApi")
public class XpTintContextWrapper extends ContextWrapper {

    /**
     * Set to {@code false} to check only {@code isLightTheme} attribute.
     * Set to {@code true} to wrap all {@link ContextThemeWrapper}s.
     */
    public static boolean FORCE = true;

    private Resources mResources = null;

    private static final WeakHashMap<Context, Boolean> CACHE = new WeakHashMap<>();

    private static boolean shouldBeUsed() {
        return Build.VERSION.SDK_INT < 21;
    }

    public static Context wrap(final Context context) {
        if (shouldBeUsed()) {
            if (context instanceof ContextThemeWrapper) {
                if (FORCE) {
                    return TintContextWrapper.wrap(new XpTintContextWrapper(context));
                }

                final Context baseContext = ((ContextThemeWrapper) context).getBaseContext();
                final boolean isLightTheme = isLightTheme(context);
                final boolean baseIsLightTheme = isLightTheme(baseContext);
                if (isLightTheme != baseIsLightTheme) {
                    return TintContextWrapper.wrap(new XpTintContextWrapper(context));
                }
            }
        }
        return context;
    }

    public static Context wrap(final Context context, @StyleRes final int themeResId) {
        final Context context2 = new ContextThemeWrapper(context, themeResId);
        return wrap(context2);
    }

    private static boolean isLightTheme(final Context context) {
        Boolean value = CACHE.get(context);
        if (value == null) {
            value = XpContext.resolveBoolean(context, android.support.v7.appcompat.R.attr.isLightTheme, false);
            CACHE.put(context, value);
        }
        return value;
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
