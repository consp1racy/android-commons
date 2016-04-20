package net.xpece.android.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

/**
 * @author Eugen on 19. 4. 2016.
 */
public class CalligraphyToolbar extends Toolbar {

    private CalligraphyToolbarHelper mCalligraphyHelper;

    public CalligraphyToolbar(final Context context) {
        super(context);
    }

    public CalligraphyToolbar(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    public CalligraphyToolbar(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private CalligraphyToolbarHelper getCalligraphyHelper() {
        // We have to late-init this because super constructor calls setTitle before our constructor.
        if (mCalligraphyHelper == null) {
            mCalligraphyHelper = new CalligraphyToolbarHelper(this);
        }
        return mCalligraphyHelper;
    }

    public void setTitleTextAppearance(@StyleRes final int resId) {
        setTitleTextAppearance(getContext(), resId);
    }

    public void setSubtitleTextAppearance(@StyleRes final int resId) {
        setSubtitleTextAppearance(getContext(), resId);
    }

    @Override
    public void setTitle(final CharSequence title) {
        super.setTitle(title);
        getCalligraphyHelper().onSetTitle();
    }

    @Override
    public void setTitleTextAppearance(final Context context, @StyleRes final int resId) {
        super.setTitleTextAppearance(context, resId);
        getCalligraphyHelper().onSetTitleTextAppearance();
    }

    @Override
    public void setSubtitle(final CharSequence subtitle) {
        super.setSubtitle(subtitle);
        getCalligraphyHelper().onSetSubtitle();
    }

    @Override
    public void setSubtitleTextAppearance(final Context context, @StyleRes final int resId) {
        super.setSubtitleTextAppearance(context, resId);
        getCalligraphyHelper().onSetSubtitleTextAppearance();
    }
}
