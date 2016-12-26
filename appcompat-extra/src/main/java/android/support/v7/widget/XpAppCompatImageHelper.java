package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.widget.ImageView;

import net.xpece.android.appcompatextra.R;
import net.xpece.android.widget.XpImageViewCompat;

/**
 * @hide
 */
@SuppressWarnings("RestrictedApi")
@RestrictTo(RestrictTo.Scope.GROUP_ID)
public final class XpAppCompatImageHelper {

    private final ImageView mView;
    private final AppCompatDrawableManager mDrawableManager;

    private TintInfo mInternalTint;
    private TintInfo mTint;

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

            int srcResId = a.getResourceId(R.styleable.XpAppCompatImageHelper_android_src, -1);
            if (a.hasValue(R.styleable.XpAppCompatImageHelper_srcCompat)) {
                srcResId = a.getResourceId(R.styleable.XpAppCompatImageHelper_srcCompat, -1);
            }
            if (srcResId != -1) {
                ColorStateList tint = mDrawableManager.getTintList(mView.getContext(), srcResId);
                if (tint != null) {
                    setInternalTint(tint);
                }
            }
            if (a.hasValue(R.styleable.XpAppCompatImageHelper_tint)) {
                XpImageViewCompat.setImageTintList(mView, a.getColorStateList(R.styleable.XpAppCompatImageHelper_tint));
            }
            if (a.hasValue(R.styleable.XpAppCompatImageHelper_tintMode)) {
                XpImageViewCompat.setImageTintMode(mView, DrawableUtils.parseTintMode(a.getInt(R.styleable.XpAppCompatImageHelper_tintMode, -1), null));
            }
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    public void onSetImageResource(int resId) {
        // Update the default background tint
        setInternalTint(mDrawableManager != null
            ? mDrawableManager.getTintList(mView.getContext(), resId)
            : null);
    }

    public void onSetImageDrawable(Drawable d) {
        // We don't know that this drawable is, so we need to clear the default background tint
        setInternalTint(null);
    }

    public void setSupportTintList(ColorStateList tint) {
        if (mTint == null) {
            mTint = new TintInfo();
        }
        mTint.mTintList = tint;
        mTint.mHasTintList = true;

        applySupportTint();
    }

    public ColorStateList getSupportTintList() {
        return mTint != null ? mTint.mTintList : null;
    }

    public void setSupportTintMode(PorterDuff.Mode tintMode) {
        if (mTint == null) {
            mTint = new TintInfo();
        }
        mTint.mTintMode = tintMode;
        mTint.mHasTintMode = true;

        applySupportTint();
    }

    public PorterDuff.Mode getSupportTintMode() {
        return mTint != null ? mTint.mTintMode : null;
    }

    public void applySupportTint() {
        final Drawable d = mView.getDrawable();
        if (d != null) {
            if (mTint != null) {
                AppCompatDrawableManager.tintDrawable(d, mTint, mView.getDrawableState());
            } else if (mInternalTint != null) {
                AppCompatDrawableManager.tintDrawable(d, mInternalTint, mView.getDrawableState());
            }
        }
    }

    private void setInternalTint(ColorStateList tint) {
        if (tint != null) {
            if (mInternalTint == null) {
                mInternalTint = new TintInfo();
            }
            mInternalTint.mTintList = tint;
            mInternalTint.mHasTintList = true;
        } else {
            mInternalTint = null;
        }
        applySupportTint();
    }
}
