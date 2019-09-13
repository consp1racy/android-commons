package net.xpece.android.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import androidx.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by pechanecjr on 14. 11. 2014.
 */
public class AspectLockedFrameLayout extends FrameLayout {
    private static final String TAG = AspectLockedFrameLayout.class.getSimpleName();

    public static final int ADJUST_DETERMINE = 0;
    public static final int ADJUST_HEIGHT = 1;
    public static final int ADJUST_WIDTH = 2;

    @IntDef({ADJUST_DETERMINE, ADJUST_HEIGHT, ADJUST_WIDTH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Adjust {
    }

    private float mAspectRatio;
    private int mAdjust;

    public AspectLockedFrameLayout(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public AspectLockedFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public AspectLockedFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AspectLockedFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ta = context.obtainStyledAttributes(attrs, net.xpece.android.R.styleable.AspectLockedImageView, defStyleAttr, defStyleRes);
        float width = ta.getInteger(net.xpece.android.R.styleable.AspectLockedImageView_aspectRatioWidth, 1);
        float height = ta.getInteger(net.xpece.android.R.styleable.AspectLockedImageView_aspectRatioHeight, 1);
        mAdjust = ta.getInteger(net.xpece.android.R.styleable.AspectLockedImageView_aspectRatioAdjust, 0);
        ta.recycle();
        mAspectRatio = width / height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mAspectRatio == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            onMeasureWithParams(widthMeasureSpec, heightMeasureSpec, mAspectRatio);
        }
    }

    @SuppressLint("WrongCall")
    private void onMeasureWithParams(int widthMeasureSpec, int heightMeasureSpec, float aspectRatio) {
        int lockedWidth = MeasureSpec.getSize(widthMeasureSpec);
        int lockedHeight = MeasureSpec.getSize(heightMeasureSpec);

        boolean adjustDetermineHeight = false;
        if (mAdjust == ADJUST_DETERMINE) {
            if ((lockedHeight != 0 && lockedWidth != 0) || (lockedHeight == 0 && lockedWidth == 0)) {
                throw new IllegalStateException("Exactly one of height or width must be set to zero when aspectRatioAdjust=\"determine\" is used.");
            } else if (lockedHeight == 0) {
                adjustDetermineHeight = true;
            }
        }

        // Get the padding of the border background.
        int hPadding = getPaddingLeft() + getPaddingRight();
        int vPadding = getPaddingTop() + getPaddingBottom();

        // Resize the preview frame with correct aspect ratio.
        lockedWidth -= hPadding;
        lockedHeight -= vPadding;

        if (mAdjust == ADJUST_WIDTH) {
            lockedWidth = (int) (lockedHeight * aspectRatio + .5);
        } else if (mAdjust == ADJUST_HEIGHT) {
            lockedHeight = (int) (lockedWidth / aspectRatio + .5);
        } else if (mAdjust == ADJUST_DETERMINE) {
            if (adjustDetermineHeight) {
                lockedHeight = (int) (lockedWidth / aspectRatio + .5);
            } else {
                lockedWidth = (int) (lockedHeight * aspectRatio + .5);
            }
        }

        // Add the padding of the border.
        lockedWidth += hPadding;
        lockedHeight += vPadding;

        // Ask children to follow the new preview dimension.
//        setMeasuredDimension(lockedWidth, lockedHeight);
        super.onMeasure(MeasureSpec.makeMeasureSpec(lockedWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(lockedHeight, MeasureSpec.EXACTLY));
    }

    public void setAspectRatio(float aspectRatio) {
        if (aspectRatio < 0.0) {
            throw new IllegalArgumentException(
                "Aspect ratio must be positive");
        }

        if (mAspectRatio != aspectRatio) {
            mAspectRatio = aspectRatio;
            requestLayout();
        }
    }

    public void setAdjust(@Adjust int adjust) {
        if (mAdjust != adjust) {
            mAdjust = adjust;
            requestLayout();
        }
    }
}
