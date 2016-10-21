package net.xpece.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;

import net.xpece.android.R;

/**
 * @author Eugen on 28.09.2016.
 */

public class CheckedFrameLayout extends FrameLayout implements Checkable {

    private static final int[] DRAWABLE_STATE_CHECKED = new int[]{android.R.attr.state_checked};

    private boolean mChecked;

    public CheckedFrameLayout(final Context context) {
        this(context, null);
    }

    public CheckedFrameLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckedFrameLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CheckedFrameLayout, defStyleAttr, 0);
        final boolean checked = ta.getBoolean(R.styleable.CheckedFrameLayout_android_checked, false);
        setChecked(checked);
        ta.recycle();
    }

    @Override
    public void setChecked(final boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        if (mChecked) {
            return mergeDrawableStates(
                super.onCreateDrawableState(extraSpace + DRAWABLE_STATE_CHECKED.length),
                DRAWABLE_STATE_CHECKED);
        } else {
            return super.onCreateDrawableState(extraSpace);
        }
    }
}
