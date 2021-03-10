package net.xpece.android.text

import android.text.SpannableString
import android.text.Spanned

class Emphasis(
        /**
         * The span to apply as emphasis.
         *
         * This must be stateless.
         */
        var emphasisSpan: Any = BOLD
) {

    /**
     * Return [text] as [CharSequence] with occurrences of [emphasis] emphasized by [emphasisSpan].
     *
     * String comparison is done literally. You should pre-process both strings beforehand:
     * strip diacritics and convert to lower case. Do this off main thread and make it available
     * from your data layer.
     */
    fun emphasize(
            text: String,
            emphasis: CharSequence
    ): CharSequence {
        val lenEmp = emphasis.length
        if (lenEmp == 0) {
            // Emphasized string is empty. Return the un-emphasized original.
            return text
        }

        val lenTotal = text.length
        if (lenEmp > lenTotal) {
            // Emphasized string doesn't fit. Return the un-emphasized original.
            return text
        }

        return SpannableString(text).apply {
            emphasize(this, text, emphasis.toString())
        }
    }

    private fun emphasize(
            spannable: SpannableString,
            text: String,
            emphasis: String
    ) {
        if (text == emphasis) {
            // We have a perfect match!
            spannable.setSpan(emphasisSpan, 0, spannable.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } else {
            val lenEmp = emphasis.length
            var next = text.indexOf(emphasis)
            while (next != -1) {
                // emphasize only first letters of words
                // this behavior reflects that of the filter
                if (next == 0 || text[next - 1].isWhitespace()) {
                    spannable.setSpan(emphasisSpan, next, next + lenEmp, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                next = text.indexOf(emphasis, next + lenEmp)
            }
        }
    }
}
