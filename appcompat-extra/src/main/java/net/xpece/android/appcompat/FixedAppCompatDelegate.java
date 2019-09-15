package net.xpece.android.appcompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatCallback;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.FixedAppCompatDelegateImpl;

/**
 * {@link AppCompatDelegate} which passes correct themed context to {@code include} XML layouts
 * on Android 4.x.
 * See <a href="https://issuetracker.google.com/issues/138883156">issue 138883156</a>.
 * <p></p>
 * Usage in an existing {@link androidx.appcompat.app.AppCompatActivity AppCompatActivity}:
 * <pre>
 * private AppCompatDelegate mDelegate = null;
 *
 * &#64;Override
 * &#64;NonNull
 * public AppCompatDelegate getDelegate() {
 *     if (mDelegate == null) {
 *         mDelegate = FixedAppCompatDelegate.create(this, this);
 *     }
 *     return mDelegate;
 * }
 * </pre>
 */
public final class FixedAppCompatDelegate {

    /**
     * Create an {@link AppCompatDelegate} to use with {@code activity}.
     *
     * @param callback An optional callback for AppCompat specific events
     */
    @NonNull
    public static AppCompatDelegate create(@NonNull Activity activity,
                                           @Nullable AppCompatCallback callback) {
        return new FixedAppCompatDelegateImpl(activity, callback);
    }

    /**
     * Create an {@link AppCompatDelegate} to use with {@code dialog}.
     *
     * @param callback An optional callback for AppCompat specific events
     */
    @NonNull
    public static AppCompatDelegate create(@NonNull Dialog dialog,
                                           @Nullable AppCompatCallback callback) {
        return new FixedAppCompatDelegateImpl(dialog, callback);
    }

    /**
     * Create an {@link AppCompatDelegate} to use with a {@code context}
     * and a {@code window}.
     *
     * @param callback An optional callback for AppCompat specific events
     */
    @NonNull
    public static AppCompatDelegate create(@NonNull Context context, @NonNull Window window,
                                           @Nullable AppCompatCallback callback) {
        return new FixedAppCompatDelegateImpl(context, window, callback);
    }

    /**
     * Create an {@link AppCompatDelegate} to use with a {@code context}
     * and hosted by an {@code Activity}.
     *
     * @param callback An optional callback for AppCompat specific events
     */
    @NonNull
    public static AppCompatDelegate create(@NonNull Context context, @NonNull Activity activity,
                                           @Nullable AppCompatCallback callback) {
        return new FixedAppCompatDelegateImpl(context, activity, callback);
    }

    private FixedAppCompatDelegate() {
        throw new AssertionError();
    }
}
