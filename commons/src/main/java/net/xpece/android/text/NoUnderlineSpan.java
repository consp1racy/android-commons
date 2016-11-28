package net.xpece.android.text;

import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/**
 * Removes underline e.g. from {@link android.text.style.URLSpan}.
 */

public class NoUnderlineSpan extends CharacterStyle implements UpdateAppearance {
    @Override
    public void updateDrawState(final TextPaint tp) {
        tp.setUnderlineText(false);
    }
}
