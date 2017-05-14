package net.xpece.android.transition;

import android.animation.TimeInterpolator;

/**
 * @author Eugen on 28.02.2017.
 */

public class BooleanVisibilityInterpolator implements TimeInterpolator {

    private final boolean mTargetVisible;
    private final boolean mSourceVisible;

    public BooleanVisibilityInterpolator(final boolean targetVisible) {
        mSourceVisible = !targetVisible;
        mTargetVisible = targetVisible;
    }

    public BooleanVisibilityInterpolator(final boolean sourceVisible, final boolean targetVisible) {
        mSourceVisible = sourceVisible;
        mTargetVisible = targetVisible;
    }

    @Override
    public float getInterpolation(final float input) {
        if (input == 1) {
            return mTargetVisible ? 1 : 0;
        } else {
            return mSourceVisible ? 0 : 1; // Visible while animating. This is time, not alpha!
        }
    }
}
