package androidx.appcompat.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.core.view.ViewCompat;

import net.xpece.android.appcompatextra.R;

import org.xmlpull.v1.XmlPullParser;

/**
 * {@link AppCompatDelegate} which passes correct themed context to {@code include} XML layouts
 * on Android 4.x.
 */
@SuppressLint("RestrictedApi")
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class FixedAppCompatDelegateImpl extends AppCompatDelegateImpl {

    private static final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;

    private AppCompatViewInflater mAppCompatViewInflater;

    public FixedAppCompatDelegateImpl(Activity activity, AppCompatCallback callback) {
        super(activity, callback);
    }

    public FixedAppCompatDelegateImpl(Dialog dialog, AppCompatCallback callback) {
        super(dialog, callback);
    }

    public FixedAppCompatDelegateImpl(Context context, Window window, AppCompatCallback callback) {
        super(context, window, callback);
    }

    public FixedAppCompatDelegateImpl(Context context, Activity activity, AppCompatCallback callback) {
        super(context, activity, callback);
    }

    @Override
    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        if (mAppCompatViewInflater == null) {
            final TypedArray a = mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
            try {
                String viewInflaterClassName =
                        a.getString(R.styleable.AppCompatTheme_viewInflaterClass);
                if ((viewInflaterClassName == null)
                        || AppCompatViewInflater.class.getName().equals(viewInflaterClassName)) {
                    // Either default class name or set explicitly to null. In both cases
                    // create the base inflater (no reflection)
                    mAppCompatViewInflater = new AppCompatViewInflater();
                } else {
                    try {
                        Class<?> viewInflaterClass = Class.forName(viewInflaterClassName);
                        mAppCompatViewInflater =
                                (AppCompatViewInflater) viewInflaterClass.getDeclaredConstructor()
                                        .newInstance();
                    } catch (Throwable t) {
                        Log.i(TAG, "Failed to instantiate custom view inflater "
                                + viewInflaterClassName + ". Falling back to default.", t);
                        mAppCompatViewInflater = new AppCompatViewInflater();
                    }
                }
            } finally {
                a.recycle();
            }
        }

        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            // https://issuetracker.google.com/issues/138883156
            if ((attrs instanceof XmlPullParser) && ((XmlPullParser) attrs).getDepth() > 1) {
                inheritContext = true;
            }
            if (!inheritContext) {
                inheritContext = shouldInheritContext((ViewParent) parent);
            }
        }

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                IS_PRE_LOLLIPOP, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                VectorEnabledTintResources.shouldBeUsed() /* Only tint wrap the context if enabled */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = mWindow.getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }
}
