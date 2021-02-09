package net.xpece.android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

import net.xpece.android.R;

@SuppressLint("AppCompatCustomView")
public class OverlayImageView extends ImageView {

    private Drawable mOverlay;
    private int mOverlayGravity;
    private float mOverlayPadding;

    private float mOverlayTranslateX = 0;
    private float mOverlayTranslateY = 0;

    private boolean mIsPrepared = false;
    private boolean mShowOverlay = true;

    public OverlayImageView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public OverlayImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public OverlayImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @SuppressWarnings("RestrictedApi")
    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TintTypedArray ta = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.OverlayImageView, defStyleAttr, defStyleRes);
        mOverlay = ta.getDrawable(R.styleable.OverlayImageView_oiv_overlay);
        mOverlayGravity = ta.getInt(R.styleable.OverlayImageView_oiv_overlayGravity, Gravity.TOP | GravityCompat.START);
        mOverlayGravity = GravityCompat.getAbsoluteGravity(mOverlayGravity, ViewCompat.getLayoutDirection(this));
        mOverlayPadding = ta.getDimension(R.styleable.OverlayImageView_oiv_overlayPadding, 0);
        ta.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mIsPrepared = false;

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mIsPrepared) {
            prepareOverlay();
        }

        if (mShowOverlay && mOverlay != null) {
            drawOverlay(canvas);
        }
    }

    private void drawOverlay(Canvas canvas) {
        canvas.save();
        canvas.translate(mOverlayTranslateX, mOverlayTranslateY);
        mOverlay.draw(canvas);
        canvas.restore();
    }

    @SuppressLint("RtlHardcoded")
    private void prepareOverlay() {
        if (mOverlay == null) {
            return;
        }

        // Calculate width and height
        int width = mOverlay.getIntrinsicWidth();
        int height = mOverlay.getIntrinsicHeight();

        if (mOverlay.getBounds().isEmpty()) {
            mOverlay.setBounds(0, 0, width, height);
        }

        // Calculate bounds
        if ((mOverlayGravity & Gravity.TOP) == Gravity.TOP) {
            mOverlayTranslateY = mOverlayPadding;
        } else if ((mOverlayGravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
            mOverlayTranslateY = getMeasuredHeight() - height - mOverlayPadding;
        } else if ((mOverlayGravity & Gravity.CENTER_VERTICAL) == Gravity.CENTER_VERTICAL) {
            mOverlayTranslateY = (getMeasuredHeight() - height) / 2f;
        }

        if ((mOverlayGravity & Gravity.LEFT) == Gravity.LEFT) {
            mOverlayTranslateX = mOverlayPadding;
        } else if ((mOverlayGravity & Gravity.RIGHT) == Gravity.RIGHT) {
            mOverlayTranslateX = getMeasuredWidth() - width - mOverlayPadding;
        } else if ((mOverlayGravity & Gravity.CENTER_HORIZONTAL) == Gravity.CENTER_HORIZONTAL) {
            mOverlayTranslateX = (getMeasuredWidth() - width) / 2f;
        }

        mIsPrepared = true;
    }

    public void setShowOverlay(boolean showOverlay) {
        if (showOverlay != mShowOverlay) {
            mShowOverlay = showOverlay;
            invalidate();
        }
    }

    public void setOverlayPadding(int padding) {
        if (padding != mOverlayPadding) {
            mOverlayPadding = padding;
            mIsPrepared = false;
            invalidate();
        }
    }

    public void setOverlayGravity(int gravity) {
        int newGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(this));
        if (newGravity != mOverlayGravity) {
            mOverlayGravity = newGravity;
            mIsPrepared = false;
            invalidate();
        }
    }

    public void setOverlay(Drawable overlay) {
        if (overlay != mOverlay) {
            mOverlay = overlay;
            mIsPrepared = false;
            invalidate();
        }
    }

    public void setOverlay(@DrawableRes int resId) {
        setOverlay(AppCompatResources.getDrawable(getContext(), resId));
    }
}
