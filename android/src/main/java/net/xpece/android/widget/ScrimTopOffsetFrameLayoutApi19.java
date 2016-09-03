package net.xpece.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import net.xpece.android.R;

/**
 * FrameLayout that offsets its content according to supplied window insets and scrims top.
 * Can be used as child of SingleLayout.
 * Do not use as top level container. Anything extending FrameLayout is ignored.
 * Fixed for API 19.
 */
public class ScrimTopOffsetFrameLayoutApi19 extends FrameLayout {

    private Drawable mInsetForeground;

    private Rect mInsets;

    private Rect mTempRect = new Rect();

    public ScrimTopOffsetFrameLayoutApi19(Context context) {
        this(context, null);
    }

    public ScrimTopOffsetFrameLayoutApi19(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrimTopOffsetFrameLayoutApi19(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs,
            R.styleable.ScrimInsetsFrameLayout, defStyleAttr,
            R.style.Widget_Design_ScrimInsetsFrameLayout);
        mInsetForeground = a.getDrawable(R.styleable.ScrimInsetsFrameLayout_insetForeground);
        a.recycle();
        setWillNotDraw(true); // No need to draw until the insets are adjusted

        ViewCompat.setOnApplyWindowInsetsListener(this,
            new android.support.v4.view.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsetsCompat onApplyWindowInsets(View v,
                                                              WindowInsetsCompat insets) {
                    if (null == mInsets) {
                        mInsets = new Rect();
                    }
                    mInsets.set(insets.getSystemWindowInsetLeft(),
                        insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(),
                        insets.getSystemWindowInsetBottom());
                    onInsetsChanged(mInsets);
                    setWillNotDraw(mInsets.isEmpty() || mInsetForeground == null);
                    ViewCompat.postInvalidateOnAnimation(ScrimTopOffsetFrameLayoutApi19.this);
                    return insets.replaceSystemWindowInsets(insets.getSystemWindowInsetLeft(), 0, insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
//                    return insets.consumeSystemWindowInsets();
                }
            });
    }

    @Override
    protected boolean fitSystemWindows(final Rect insets) {
        if (Build.VERSION.SDK_INT == 19 || Build.VERSION.SDK_INT == 20) {
            if (null == mInsets) {
                mInsets = new Rect();
            }
            mInsets.set(insets);
            onInsetsChanged(mInsets);
            setWillNotDraw(mInsets.isEmpty() || mInsetForeground == null);
            ViewCompat.postInvalidateOnAnimation(ScrimTopOffsetFrameLayoutApi19.this);
            insets.top = 0;

            boolean done = false;
            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View child = getChildAt(i);
//                done = child.fitSystemWindows(insets);
                done = ViewUtils.fitSystemWindows(child, insets);
                if (done) {
                    break;
                }
            }
            return done;
        } else {
            return super.fitSystemWindows(insets);
        }
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
//            mTempRect.set(0, height - mInsets.bottom, width, height);
//            mInsetForeground.setBounds(mTempRect);
//            mInsetForeground.draw(canvas);

            // Left
//            mTempRect.set(0, mInsets.top, mInsets.left, height - mInsets.bottom);
//            mInsetForeground.setBounds(mTempRect);
//            mInsetForeground.draw(canvas);

            // Right
//            mTempRect.set(width - mInsets.right, mInsets.top, width, height - mInsets.bottom);
//            mInsetForeground.setBounds(mTempRect);
//            mInsetForeground.draw(canvas);

            canvas.restoreToCount(sc);
        }
    }

    protected void onInsetsChanged(Rect insets) {
        setPadding(0, insets.top, 0, 0);
//        setPadding(insets.left, insets.top, insets.right, insets.bottom);
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

}
