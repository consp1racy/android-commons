package net.xpece.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Eugen on 19.12.2016.
 */

public class ScrimInsetsNonConsumingFrameLayout extends IrresponsibleFrameLayout {

    Drawable mInsetForeground;

    Rect mInsets;

    private Rect mTempRect = new Rect();

    public ScrimInsetsNonConsumingFrameLayout(final Context context) {
        this(context, null);
    }

    public ScrimInsetsNonConsumingFrameLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrimInsetsNonConsumingFrameLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs,
            android.support.design.R.styleable.ScrimInsetsFrameLayout, defStyleAttr,
            android.support.design.R.style.Widget_Design_ScrimInsetsFrameLayout);
        mInsetForeground = a.getDrawable(android.support.design.R.styleable.ScrimInsetsFrameLayout_insetForeground);
        a.recycle();
        setWillNotDraw(true); // No need to draw until the insets are adjusted

        ViewCompat.setOnApplyWindowInsetsListener(this,
            new android.support.v4.view.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                    if (null == mInsets) {
                        mInsets = new Rect();
                    }
                    mInsets.set(insets.getSystemWindowInsetLeft(),
                        insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(),
                        insets.getSystemWindowInsetBottom());
                    onInsetsChanged(insets);
                    setWillNotDraw(!insets.hasSystemWindowInsets() || mInsetForeground == null);
                    ViewCompat.postInvalidateOnAnimation(ScrimInsetsNonConsumingFrameLayout.this);
//                    return insets.consumeSystemWindowInsets();
                    return insets;
                }
            });
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (mInsets != null && mInsetForeground != null) {
            int sc = canvas.save();
            canvas.translate(getScrollX(), getScrollY());

            // Top
            mTempRect.set(0, 0, width, mInsets.top);
            mInsetForeground.setBounds(mTempRect);
            mInsetForeground.draw(canvas);

            // Bottom
            mTempRect.set(0, height - mInsets.bottom, width, height);
            mInsetForeground.setBounds(mTempRect);
            mInsetForeground.draw(canvas);

            // Left
            mTempRect.set(0, mInsets.top, mInsets.left, height - mInsets.bottom);
            mInsetForeground.setBounds(mTempRect);
            mInsetForeground.draw(canvas);

            // Right
            mTempRect.set(width - mInsets.right, mInsets.top, width, height - mInsets.bottom);
            mInsetForeground.setBounds(mTempRect);
            mInsetForeground.draw(canvas);

            canvas.restoreToCount(sc);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mInsetForeground != null) {
            mInsetForeground.setCallback(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mInsetForeground != null) {
            mInsetForeground.setCallback(null);
        }
    }

    public void setInsetForeground(final Drawable insetForeground) {
        if (mInsetForeground != insetForeground) {
            if (mInsetForeground != null) {
                mInsetForeground.setCallback(null);
            }
            mInsetForeground = insetForeground;
            if (mInsetForeground != null && ViewCompat.isAttachedToWindow(this)) {
                mInsetForeground.setCallback(this);
            }
        }
    }

    protected void onInsetsChanged(WindowInsetsCompat insets) {
    }

}
