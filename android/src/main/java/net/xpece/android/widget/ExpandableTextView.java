package net.xpece.android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.xpece.android.graphics.drawable.XpDrawable;
import net.xpece.commons.android.R;

/**
 * @author Eugen
 */
public class ExpandableTextView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = ExpandableTextView.class.getSimpleName();

    /* The default number of lines */
    private static final int MAX_COLLAPSED_LINES = 8;

    /* The default animation duration */
    private static final int DEFAULT_ANIM_DURATION = 300;

    protected TextView mTv;
    protected ImageButton mButton; // Button to expand/collapse

    private boolean mRelayout;
    private boolean mCollapsed = true; // Show short version as default.

    private int mMaxCollapsedLines;

    private Drawable mExpandDrawable;
    private Drawable mCollapseDrawable;

    private int mAnimationDuration;

    private boolean mAnimating;
    private boolean mFits;

    private int mCollapsedHeight;
    private int mTextHeightWithMaxLines;
    private int mMarginBetweenTxtAndBottom;
    private Runnable mMarginRunnable = new Runnable() {
        @Override
        public void run() {
            mMarginBetweenTxtAndBottom = getHeight() - mTv.getHeight();
//            Timber.d("mMarginBetweenTxtAndBottom=" + mMarginBetweenTxtAndBottom);
        }
    };

    public ExpandableTextView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView, defStyleAttr, defStyleRes);
        mMaxCollapsedLines = typedArray.getInt(R.styleable.ExpandableTextView_maxCollapsedLines, MAX_COLLAPSED_LINES);
        mAnimationDuration = typedArray.getInt(R.styleable.ExpandableTextView_animDuration, DEFAULT_ANIM_DURATION);
        mExpandDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_expandDrawable);
        mCollapseDrawable = typedArray.getDrawable(R.styleable.ExpandableTextView_collapseDrawable);
        typedArray.recycle();

        if (mExpandDrawable == null) {
            mExpandDrawable = ContextCompat.getDrawable(context, R.drawable.ic_expand_more_white_24dp);
        }
        if (mCollapseDrawable == null) {
            mCollapseDrawable = ContextCompat.getDrawable(context, R.drawable.ic_expand_less_white_24dp);
        }

        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mFits || mAnimating) {
            return;
        }
        mAnimating = true;

        mCollapsed = !mCollapsed;

        Animation animation;
        if (mCollapsed) {
            animation = new ExpandCollapseAnimation(getHeight(), mCollapsedHeight);
        } else {
            animation = new ExpandCollapseAnimation(getHeight(),
                getHeight() + mTextHeightWithMaxLines - mTv.getHeight());
        }

        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (mButton != null) {
                    mButton.setImageDrawable(mCollapsed ? mExpandDrawable : mCollapseDrawable);
                    XpDrawable.reverse(mButton.getDrawable(), mAnimationDuration);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Clear animation here to avoid repeated applyTransformation() calls
                clearAnimation();
                // Clear the animation flag
                mAnimating = false;

                // Changes TextView's height mode to LINES, ellipsis gets reapplied!
                // Known bug: last line jumps a little, but no one will care.
                setProperMaxLines();
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        clearAnimation();
        startAnimation(animation);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // while an animation is in progress, intercept all the touch events to children to
        // prevent extra clicks during the animation
        return mAnimating || mFits;
    }

    @Override
    public void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof TextView) {
            if (mTv != null) {
                throw new IllegalArgumentException("Layout already has a TextView child.");
            }
            mTv = (TextView) child;
            setupTextView();
        }
        super.addView(child, index, params);
    }

    private void setupTextView() {
        mTv.setOnClickListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mButton = (ImageButton) findViewById(R.id.expand_collapse);
        setupButton();
    }

    private void setupButton() {
        if (mButton != null) {
            mButton.setImageDrawable(mCollapsed ? mExpandDrawable : mCollapseDrawable);
            mButton.getDrawable().setLevel(0);
            mButton.setOnClickListener(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Layout layout = mTv.getLayout();
        if (layout == null || mRelayout) {
            // If text layout is null, let's get that computed first.
            mTv.setMaxLines(Integer.MAX_VALUE);

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            layout = mTv.getLayout();

            // Saves the text height w/ max lines
            mTextHeightWithMaxLines = getRealTextViewHeight(mTv); // mTv.getMeasuredHeight();
//            for (int i = 0; i < layout.getLineCount(); i++) {
//                Timber.v("Line #" + i + " width = " + layout.getLineWidth(i));
//            }
//            Timber.v(mTv.getText().toString());
//            Timber.d("First measure. mTextHeightWithMaxLines=" + mTextHeightWithMaxLines);
        } else if (getVisibility() == View.GONE) {
            // If no change, measure and return.
//            Timber.d("Plain measure.");
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mRelayout = false;

        int totalLines = layout.getLineCount();
        boolean ellipsis = hasEllipsis(layout);
//        Timber.d("Relay measure. totalLines=" + totalLines + ", ellipsis=" + ellipsis);

        // If the text fits in collapsed mode, we are done.
        if (totalLines <= mMaxCollapsedLines && !ellipsis) {
            mFits = true;

            if (mButton != null) {
                mButton.setVisibility(View.GONE);
            }

//            Timber.d("Text fits while collapsed.");
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        if (!mAnimating) {
            // Do not interrupt animation.
            // If animating this will get applied on animation end.
            setProperMaxLines();
        }

        mFits = false;

        if (mButton != null) {
            mButton.setVisibility(View.VISIBLE);
        }

        // Re-measure with new setup
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!mAnimating) {
            if (mCollapsed) {
                // Gets the margin between the TextView and this
                mTv.post(mMarginRunnable);
                // Saves the collapsed height of this ViewGroup
                mCollapsedHeight = getMeasuredHeight();
            }
        }
    }

    private void setProperMaxLines() {
        if (mCollapsed) {
            mTv.setMaxLines(mMaxCollapsedLines);
        } else {
            mTv.setMaxLines(Integer.MAX_VALUE);
        }
    }

    private boolean hasEllipsis(Layout layout) {
        for (int i = layout.getLineCount() - 1; i >= 0; i--) {
            if (layout.getEllipsisCount(i) > 0) {
                return true;
            }
        }

        return false;
    }

    public void setButton(ImageButton v) {
        mButton = v;
        setupButton();
    }

    public void setText(CharSequence text) {
        mRelayout = true;
        mTv.setText(text);
        setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    public CharSequence getText() {
        if (mTv == null) {
            return "";
        }
        return mTv.getText();
    }

    private static int getRealTextViewHeight(TextView textView) {
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        int padding = textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom();
        return textHeight + padding;
    }

    protected class ExpandCollapseAnimation extends Animation {
        private Interpolator sInterpolator = new AccelerateDecelerateInterpolator();

        private final int mStartHeight;
        private final int mEndHeight;

        public ExpandCollapseAnimation(int startHeight, int endHeight) {
            mStartHeight = startHeight;
            mEndHeight = endHeight;
            setDuration(mAnimationDuration);
            setInterpolator(sInterpolator);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final int newHeight = (int) ((mEndHeight - mStartHeight) * interpolatedTime + mStartHeight);
            mTv.setMaxHeight(newHeight - mMarginBetweenTxtAndBottom);

            // Keep this for future development. Only wrap_content has been tested.
//            mTargetView.getLayoutParams().height = newHeight;
//            mTargetView.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    public boolean isAnimating() {
        return mAnimating;
    }
}
