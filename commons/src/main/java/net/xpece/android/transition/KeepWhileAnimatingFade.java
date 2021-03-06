package net.xpece.android.transition;

import android.support.transition.Fade;

/**
 * @author Eugen on 28.02.2017.
 */

public class KeepWhileAnimatingFade {
    public static Fade create(final int fadingMode) {
        final Fade fade = new Fade(fadingMode);
        fade.setInterpolator(new BooleanVisibilityInterpolator((fadingMode & Fade.IN) == Fade.IN));
        return fade;
    }

    private KeepWhileAnimatingFade() {
        throw new AssertionError("No instances!");
    }
}
