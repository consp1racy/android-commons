package net.xpece.android.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Build;
import androidx.annotation.IntDef;
import androidx.annotation.StringRes;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class ToastCompat {

    @IntDef({Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Duration {}

    /**
     * Make a standard toast that just contains a text view.
     *
     * @param context The context to use.  Usually your {@link android.app.Application}
     * or {@link android.app.Activity} object.
     * @param text The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link Toast#LENGTH_SHORT} or
     * {@link Toast#LENGTH_LONG}
     */
    public static Toast makeText(Context context, CharSequence text, @Duration int duration) {
        return Toast.makeText(wrap(context), text, duration);
    }

    /**
     * Make a standard toast that just contains a text view with the text from a resource.
     *
     * @param context The context to use.  Usually your {@link android.app.Application}
     * or {@link android.app.Activity} object.
     * @param resId The resource id of the string resource to use.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link Toast#LENGTH_SHORT} or
     * {@link Toast#LENGTH_LONG}
     * @throws Resources.NotFoundException if the resource can't be found.
     */
    public static Toast makeText(Context context, @StringRes int resId, @Duration int duration)
            throws Resources.NotFoundException {
        return Toast.makeText(wrap(context), context.getResources().getText(resId), duration);
    }

    private static Context wrap(final Context context) {
        if (Build.VERSION.SDK_INT == 25) {
            return new FixedContext(context);
        } else {
            return context;
        }
    }

    private static class FixedContext extends ContextWrapper {
        public FixedContext(final Context base) {
            super(base);
        }

        @Override
        public Object getSystemService(final String name) {
            if (Context.WINDOW_SERVICE.equals(name)) {
                return new FixedWindowManager((WindowManager) super.getSystemService(Context.WINDOW_SERVICE), null);
            } else {
                return super.getSystemService(name);
            }
        }

        @Override
        public Context getApplicationContext() {
            return new FixedContext(super.getApplicationContext());
        }
    }

    private static class FixedWindowManager extends WindowManagerWrapper {
        private final Runnable mOnBadTokenException;

        FixedWindowManager(final WindowManager impl, final Runnable onBadTokenException) {
            super(impl);
            mOnBadTokenException = onBadTokenException;
        }

        @Override
        public void addView(final View view, final ViewGroup.LayoutParams params) {
            try {
                super.addView(view, params);
            } catch (BadTokenException ex) {
                if (mOnBadTokenException != null) {
                    mOnBadTokenException.run();
                }
            }
        }
    }

    private static class WindowManagerWrapper implements WindowManager {
        private final WindowManager mBase;

        WindowManagerWrapper(final WindowManager base) {
            mBase = base;
        }

        @Override
        public Display getDefaultDisplay() {
            return mBase.getDefaultDisplay();
        }

        @Override
        public void removeViewImmediate(final View view) {
            mBase.removeViewImmediate(view);
        }

        @Override
        public void addView(final View view, final ViewGroup.LayoutParams params) {
            mBase.addView(view, params);
        }

        @Override
        public void updateViewLayout(final View view, final ViewGroup.LayoutParams params) {
            mBase.updateViewLayout(view, params);
        }

        @Override
        public void removeView(final View view) {
            mBase.removeView(view);
        }
    }
}
