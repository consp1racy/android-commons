package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.view.TintableBackgroundView;

import net.xpece.android.R;

@SuppressWarnings("RestrictedApi")
public class XpAppCompatView extends View implements TintableBackgroundView {
    private AppCompatBackgroundHelper mBackgroundTintHelper;

    private int mBackgroundResource;

    public XpAppCompatView(Context context) {
        this(context, null);
    }

    public XpAppCompatView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XpAppCompatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(TintContextWrapper.wrap(context), attrs, defStyleAttr);

        mBackgroundTintHelper = new AppCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);

        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.ViewBackgroundHelper, defStyleAttr, 0);
        try {
            if (a.hasValue(R.styleable.ViewBackgroundHelper_android_background)) {
                setBackgroundResource(a.getResourceId(R.styleable.ViewBackgroundHelper_android_background, 0));
            }
        } finally {
            a.recycle();
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBackgroundResource(@DrawableRes int resId) {
//        super.setBackgroundResource(resId);
//        if (mBackgroundTintHelper != null) {
//            mBackgroundTintHelper.onSetBackgroundResource(resId);
//        }

        if (resId != 0 && resId == mBackgroundResource) {
            return;
        }

        Drawable d = null;
        if (resId != 0) {
            d = AppCompatDrawableManager.get().getDrawable(getContext(), resId);
        }
        setBackgroundDrawable(d);

        mBackgroundResource = resId;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBackgroundDrawable(@Nullable Drawable background) {
        mBackgroundResource = 0;
        super.setBackgroundDrawable(background);
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.onSetBackgroundDrawable(background);
        }
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        mBackgroundResource = 0;
        super.setBackgroundColor(color);
    }

    /**
     * This should be accessed via
     * {@link androidx.core.view.ViewCompat#setBackgroundTintList(View, ColorStateList)}
     */
    @Override
    public void setSupportBackgroundTintList(@Nullable ColorStateList tint) {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.setSupportBackgroundTintList(tint);
        }
    }

    /**
     * This should be accessed via
     * {@link androidx.core.view.ViewCompat#getBackgroundTintList(View)}
     */
    @Override
    @Nullable
    public ColorStateList getSupportBackgroundTintList() {
        return mBackgroundTintHelper != null
            ? mBackgroundTintHelper.getSupportBackgroundTintList() : null;
    }

    /**
     * This should be accessed via
     * {@link androidx.core.view.ViewCompat#setBackgroundTintMode(View, PorterDuff.Mode)}
     */
    @Override
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.setSupportBackgroundTintMode(tintMode);
        }
    }

    /**
     * This should be accessed via
     * {@link androidx.core.view.ViewCompat#getBackgroundTintMode(View)}
     */
    @Override
    @Nullable
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return mBackgroundTintHelper != null
            ? mBackgroundTintHelper.getSupportBackgroundTintMode() : null;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySupportBackgroundTint();
        }
    }
}
