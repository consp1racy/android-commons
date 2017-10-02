package net.xpece.android.widget;

import android.support.annotation.StyleRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import net.xpece.android.content.res.XpCalligraphyUtils;

import java.lang.ref.WeakReference;

/**
 * @author Eugen on 19. 4. 2016.
 *
 * @deprecated Support library version 26 supports fonts out-of-the-box.
 */
@Deprecated
public final class CalligraphyToolbarHelper {

    private WeakReference<TextView> mTitleTextView = new WeakReference<>(null);
    private int mTitleTextAppearance = 0;

    private WeakReference<TextView> mSubtitleTextView = new WeakReference<>(null);
    private int mSubtitleTextAppearance = 0;

    private final Toolbar mToolbar;

    public CalligraphyToolbarHelper(final Toolbar toolbar) {
        mToolbar = toolbar;
    }

    public void onSetTitle() {
        applyTitleFont();
    }

    public void onSetTitleTextAppearance() {
        applyTitleFont();
    }

    public void onSetSubtitle() {
        applySubtitleFont();
    }

    public void onSetSubtitleTextAppearance() {
        applySubtitleFont();
    }

    private void applyTitleFont() {
        Toolbar toolbar = mToolbar;
        if (!TextUtils.isEmpty(mToolbar.getTitle())) {
            TextView titleTextView = XpToolbar.getTitleTextView(toolbar);
            if (titleTextView != null) {
                @StyleRes int titleTextAppearance = XpToolbar.getTitleTextAppearance(toolbar);
                if (titleTextAppearance != 0) {
                    boolean changed = false;
                    if (titleTextView != mTitleTextView.get()) {
                        mTitleTextView = new WeakReference<>(titleTextView);
                        changed = true;
                    }
                    if (mTitleTextAppearance != titleTextAppearance) {
                        mTitleTextAppearance = titleTextAppearance;
                        changed = true;
                    }
                    if (changed) {
                        XpCalligraphyUtils.applyFontToTextView(titleTextView, titleTextAppearance);
                    }
                }
            }
        }
    }

    private void applySubtitleFont() {
        Toolbar toolbar = mToolbar;
        if (!TextUtils.isEmpty(mToolbar.getSubtitle())) {
            TextView subtitleTextView = XpToolbar.getSubtitleTextView(toolbar);
            if (subtitleTextView != null) {
                @StyleRes int subtitleTextAppearance = XpToolbar.getSubtitleTextAppearance(toolbar);
                if (subtitleTextAppearance != 0) {
                    boolean changed = false;
                    if (subtitleTextView != mSubtitleTextView.get()) {
                        mSubtitleTextView = new WeakReference<>(subtitleTextView);
                        changed = true;
                    }
                    if (mSubtitleTextAppearance != subtitleTextAppearance) {
                        mSubtitleTextAppearance = subtitleTextAppearance;
                        changed = true;
                    }
                    if (changed) {
                        XpCalligraphyUtils.applyFontToTextView(subtitleTextView, subtitleTextAppearance);
                    }
                }
            }
        }
    }
}
