package net.xpece.android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.IntDef;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by pechanecjr on 14. 11. 2014.
 */
public class AspectLockedImageView extends AppCompatImageView {
    private static final String TAG = AspectLockedImageView.class.getSimpleName();

    public static final int ADJUST_DETERMINE = 0;
    public static final int ADJUST_HEIGHT = 1;
    public static final int ADJUST_WIDTH = 2;

    @IntDef({ADJUST_DETERMINE, ADJUST_HEIGHT, ADJUST_WIDTH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Adjust {
    }

    public static final int SOURCE_SPECIFIED = 0;
    public static final int SOURCE_DRAWABLE = 1;

    @IntDef({SOURCE_DRAWABLE, SOURCE_SPECIFIED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AspectRatioSource {
    }

    private float mAspectRatio;
    private int mAdjust;
    private int mSource;

    public AspectLockedImageView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public AspectLockedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public AspectLockedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ta = context.obtainStyledAttributes(attrs, net.xpece.android.R.styleable.AspectLockedImageView, defStyleAttr, defStyleRes);
        float width = ta.getInteger(net.xpece.android.R.styleable.AspectLockedImageView_aspectRatioWidth, 1);
        float height = ta.getInteger(net.xpece.android.R.styleable.AspectLockedImageView_aspectRatioHeight, 1);
        mAdjust = ta.getInteger(net.xpece.android.R.styleable.AspectLockedImageView_aspectRatioAdjust, 0);
        mSource = ta.getInteger(net.xpece.android.R.styleable.AspectLockedImageView_aspectRatioSource, 0);
        ta.recycle();
        mAspectRatio = width / height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mSource == SOURCE_DRAWABLE) {
            Drawable d = getDrawable();
            if (d == null) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                float aspectRatio = (float) d.getIntrinsicWidth() / d.getIntrinsicHeight();
                onMeasureWithParams(widthMeasureSpec, heightMeasureSpec, aspectRatio);
            }
        } else {
            if (mAspectRatio == 0) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                onMeasureWithParams(widthMeasureSpec, heightMeasureSpec, mAspectRatio);
            }
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

        if (XpImageView.getAdjustViewBounds(this)) {
            Drawable d = getDrawable();
            float drawableAspectRatio = aspectRatio;
            if (d != null) {
                drawableAspectRatio = (float) d.getIntrinsicWidth() / (float) d.getIntrinsicHeight();
            }
            if (drawableAspectRatio < aspectRatio - 0.0000001) {
                // adjust view width
                lockedWidth = (int) (lockedHeight * drawableAspectRatio + .5);
            } else if (drawableAspectRatio > aspectRatio + 0.0000001) {
                // adjust view height
                lockedHeight = (int) (lockedWidth / drawableAspectRatio + .5);
            }
        }

        // Add the padding of the border.
        lockedWidth += hPadding;
        lockedHeight += vPadding;

        // Ask children to follow the new preview dimension.
        setMeasuredDimension(lockedWidth, lockedHeight);
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

    public void setAspectRatioSource(@AspectRatioSource int source) {
        if (mSource != source) {
            mSource = source;
            requestLayout();
        }
    }
}
