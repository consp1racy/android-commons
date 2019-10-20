package net.xpece.android.text.span

import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

/**
 * Removes underline e.g. from [android.text.style.URLSpan].
 */
class NoUnderlineSpan : CharacterStyle(), UpdateAppearance {

    override fun updateDrawState(tp: TextPaint) {
        tp.isUnderlineText = false
    }
}
