package net.xpece.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import net.xpece.android.R;

/**
 * Created by Eugen on 10. 2. 2015.
 * Sauce: http://stackoverflow.com/a/7875656/2444099
 *
 * @author speedplane
 */
public class FontFitTextView extends TextView {
    private static final float THRESHOLD = 0.5f;

    //Attributes
    private Paint mTestPaint;

    private float mMinTextSize;
    private float mMaxTextSize;

    public FontFitTextView(Context context) {
        super(context);
        initialize(context, null);
    }

    public FontFitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        mTestPaint = new Paint();
        mTestPaint.set(getPaint());
        //max size defaults to the initially specified text size unless it is too small

        float textSize = getTextSize();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontFitTextView);
        mMinTextSize = ta.getDimension(R.styleable.FontFitTextView_minTextSize, textSize);
        mMaxTextSize = ta.getDimension(R.styleable.FontFitTextView_maxTextSize, textSize);
        ta.recycle();

        if (mMinTextSize > mMaxTextSize) {
            throw new IllegalArgumentException("Minimum text size cannot be larger than maximum text size.");
        }
    }

    /* Re size the font so the specified text fits in the text box
     * assuming the text box is the specified width.
     */
    private void refitText(CharSequence text, int textWidth) {
        if (textWidth <= 0)
            return;
        int targetWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
        float hi = mMaxTextSize;
        float lo = mMinTextSize;

        mTestPaint.set(getPaint());

        while ((hi - lo) > THRESHOLD) {
            float size = (hi + lo) / 2;
            mTestPaint.setTextSize(size);
            if (mTestPaint.measureText(text, 0, text.length()) >= targetWidth)
                hi = size; // too big
            else
                lo = size; // too small
        }

        // Use lo so that we undershoot rather than overshoot
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, lo);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int height = getMeasuredHeight();
        refitText(getText(), parentWidth);
        setMeasuredDimension(parentWidth, height);
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
        refitText(text, getWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw) {
            refitText(getText(), w);
        }
    }

    public void setMinTextSize(float size) {
        if (mMinTextSize != size) {
            mMinTextSize = size;
            onTextSizeChanged();
        }
    }

    public void setMaxTextSize(float size) {
        if (mMaxTextSize != size) {
            mMaxTextSize = size;
            onTextSizeChanged();
        }
    }

    public float getMinTextSize() {
        return mMinTextSize;
    }

    public float getMaxTextSize() {
        return mMaxTextSize;
    }

    public void onTextSizeChanged() {
        refitText(getText(), getWidth());
    }

}
