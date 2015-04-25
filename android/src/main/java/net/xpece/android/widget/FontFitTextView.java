package net.xpece.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import net.xpece.commons.android.R;

/**
 * Created by Eugen on 10. 2. 2015.
 * Sauce: http://stackoverflow.com/a/7875656/2444099
 *
 * @author speedplane
 */
public class FontFitTextView extends TextView {

    //Attributes
    private Paint mTestPaint;

    private float mMinTextSize;
    private float mMaxTextSize;

    public FontFitTextView(Context context) {
        super(context);
        initialise(context, null);
    }

    public FontFitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise(context, attrs);
    }

    private void initialise(Context context, AttributeSet attrs) {
        mTestPaint = new Paint();
        mTestPaint.set(getPaint());
        //max size defaults to the initially specified text size unless it is too small

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontFitTextView);
        mMinTextSize = ta.getDimension(R.styleable.FontFitTextView_minTextSize, 1);
        mMaxTextSize = ta.getDimension(R.styleable.FontFitTextView_maxTextSize, Float.MAX_VALUE);
        ta.recycle();
    }

    /* Re size the font so the specified text fits in the text box
     * assuming the text box is the specified width.
     */
    private void refitText(CharSequence text, int textWidth) {
        if (textWidth <= 0)
            return;
        int targetWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
        float hi = 100;
        float lo = 2;
        final float threshold = 0.5f; // How close we have to be

        mTestPaint.set(getPaint());

        while ((hi - lo) > threshold) {
            float size = (hi + lo) / 2;
            mTestPaint.setTextSize(size);
            if (mTestPaint.measureText(text, 0, text.length()) >= targetWidth)
                hi = size; // too big
            else
                lo = size; // too small
        }
        // Use lo so that we undershoot rather than overshoot
        float size = lo;

        if (size < mMinTextSize) size = mMinTextSize;
        if (size > mMaxTextSize) size = mMaxTextSize;
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
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
