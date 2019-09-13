package com.google.android.material.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.navigation.NavigationView;

import java.util.HashSet;
import java.util.Set;

/**
 * @deprecated Use {@link NavigationView} and {@link ViewCompat#setOnApplyWindowInsetsListener(View, androidx.core.view.OnApplyWindowInsetsListener)}
 */
@Deprecated
public class XpNavigationView extends NavigationView {
    private static final Rect TEMP = new Rect();
    private Set<OnInsetsChangedListener> mOnInsetsChangedListeners = new HashSet<>();
    private Rect mInsets = new Rect();

    public XpNavigationView(Context context) {
        super(context);
    }

    public XpNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XpNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("RestrictedApi")
    @Override
    protected void onInsetsChanged(WindowInsetsCompat insets) {
        super.onInsetsChanged(insets);
        mInsets.set(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
        callOnInsetsChangedListeners(mInsets);
    }

    public void addOnInsetsChangedListener(OnInsetsChangedListener l) {
        mOnInsetsChangedListeners.add(l);

        final Rect temp = TEMP;
        temp.set(mInsets);
        callOnInsetsChangedListener(l, temp);
    }

    public void removeOnInsetsChangedListener(OnInsetsChangedListener l) {
        mOnInsetsChangedListeners.remove(l);
    }

    private void callOnInsetsChangedListeners(Rect insets) {
        final Rect temp = TEMP;
        temp.set(insets);
        for (OnInsetsChangedListener l : mOnInsetsChangedListeners) {
            callOnInsetsChangedListener(l, temp);
        }
    }

    private void callOnInsetsChangedListener(OnInsetsChangedListener l, Rect insets) {
        l.onInsetsChanged(insets);
    }

    public interface OnInsetsChangedListener {
        void onInsetsChanged(Rect insets);
    }
}
