package net.xpece.android.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import net.xpece.commons.android.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by pechanecjr on 14. 11. 2014.
 */
public class AspectLockedImageView extends ImageView {
    private static final String TAG = AspectLockedImageView.class.getSimpleName();

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AspectLockedImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AspectLockedImageView, defStyleAttr, defStyleRes);
        float width = ta.getInteger(R.styleable.AspectLockedImageView_aspectRatioWidth, 1);
        float height = ta.getInteger(R.styleable.AspectLockedImageView_aspectRatioHeight, 1);
        mAdjust = ta.getInteger(R.styleable.AspectLockedImageView_aspectRatioAdjust, 0);
        mSource = ta.getInteger(R.styleable.AspectLockedImageView_aspectRatioSource, 0);
        ta.recycle();
        mAspectRatio = width / height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int lockedWidth = MeasureSpec.getSize(widthMeasureSpec);
        int lockedHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (mSource == AspectRatioSource.DRAWABLE) {
            Drawable d = getDrawable();
            if (d == null) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                float aspectRatio = (float) d.getIntrinsicWidth() / d.getIntrinsicHeight();
                onMeasureWithParams(lockedWidth, lockedHeight, aspectRatio);
            }
        } else {
            if (mAspectRatio == 0) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                onMeasureWithParams(lockedWidth, lockedHeight, mAspectRatio);
            }
        }
    }

    @SuppressLint("WrongCall")
    private void onMeasureWithParams(int lockedWidth, int lockedHeight, float aspectRatio) {
        // Get the padding of the border background.
        int hPadding = getPaddingLeft() + getPaddingRight();
        int vPadding = getPaddingTop() + getPaddingBottom();

        // Resize the preview frame with correct aspect ratio.
        lockedWidth -= hPadding;
        lockedHeight -= vPadding;

        if (mAdjust == Adjust.WIDTH) {
            lockedWidth = (int) (lockedHeight * aspectRatio + .5);
        } else if (mAdjust == Adjust.HEIGHT) {
            lockedHeight = (int) (lockedWidth / aspectRatio + .5);
        } else if (mAdjust == Adjust.DETERMINE) {
            if (lockedHeight > 0 && lockedWidth == 0) {
                lockedWidth = (int) (lockedHeight * aspectRatio + .5);
            } else if (lockedWidth > 0 && lockedHeight == 0) {
                lockedHeight = (int) (lockedWidth / aspectRatio + .5);
            } else {
                throw new IllegalStateException("Exactly one of height or width must be set to zero when Adjust.DETERMINE is used.");
            }
        }

        // Add the padding of the border.
        lockedWidth += hPadding;
        lockedHeight += vPadding;

        // Ask children to follow the new preview dimension.
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

    public void setAspectRatioSource(@AspectRatioSource int source) {
        if (mSource != source) {
            mSource = source;
            requestLayout();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Adjust {
        @Adjust
        int DETERMINE = 0;
        @Adjust
        int HEIGHT = 1;
        @Adjust
        int WIDTH = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AspectRatioSource {
        @Adjust
        int SPECIFIED = 0;
        @Adjust
        int DRAWABLE = 1;
    }
}
