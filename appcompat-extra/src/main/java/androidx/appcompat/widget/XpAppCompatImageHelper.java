package androidx.appcompat.widget;

import androidx.annotation.RestrictTo;
import android.util.AttributeSet;
import android.widget.ImageView;

import net.xpece.android.appcompatextra.R;

/**
 * @hide
 */
@SuppressWarnings("RestrictedApi")
@RestrictTo(RestrictTo.Scope.GROUP_ID)
public final class XpAppCompatImageHelper {

    private final ImageView mView;
    private final AppCompatDrawableManager mDrawableManager;

    public XpAppCompatImageHelper(ImageView view) {
        mView = view;
        mDrawableManager = AppCompatDrawableManager.get();
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        TintTypedArray a = null;
        try {
            a = TintTypedArray.obtainStyledAttributes(mView.getContext(), attrs,
                R.styleable.XpAppCompatImageHelper, defStyleAttr, 0);

            // ImageView does not read enabled state from XML. Fuck me, right?
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
