package androidx.appcompat.widget;

import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.RestrictTo;

import net.xpece.android.appcompatextra.R;

@SuppressWarnings("RestrictedApi")
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@Deprecated
public final class XpAppCompatImageHelper {

    private final ImageView mView;

    public XpAppCompatImageHelper(ImageView view) {
        mView = view;
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        TintTypedArray a = null;
        try {
            a = TintTypedArray.obtainStyledAttributes(mView.getContext(), attrs,
                R.styleable.XpAppCompatImageHelper, defStyleAttr, 0);

            if (a.hasValue(R.styleable.XpAppCompatImageHelper_android_enabled)) {
                mView.setEnabled(a.getBoolean(R.styleable.XpAppCompatImageHelper_android_enabled, mView.isEnabled()));
            }
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }
}
