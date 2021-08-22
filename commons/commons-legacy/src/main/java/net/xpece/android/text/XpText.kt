@file:JvmName("XpText")

package net.xpece.android.text

import android.graphics.Typeface
import android.os.Build
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import java.text.Normalizer
import java.util.regex.Pattern

private val PATTERN_UNACCENT = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")

/**
 * Source: http://www.rgagnon.com/javadetails/java-0456.html
 */
fun CharSequence.removeDiacritics(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return PATTERN_UNACCENT.matcher(temp).replaceAll("")
}

// Medium Span =====================================================================================

internal val BOLD = StyleSpan(Typeface.BOLD)

private val IMPL: TextUtilsImpl = if (Build.VERSION.SDK_INT >= 21) {
    LollipopTextUtilsImpl()
} else {
    BaseTextUtilsImpl()
}

fun getMediumSpan() = IMPL.getMediumSpan()

internal interface TextUtilsImpl {

    fun getMediumSpan(): Any
}

internal open class BaseTextUtilsImpl : TextUtilsImpl {

    override fun getMediumSpan(): Any = BOLD
}

internal open class LollipopTextUtilsImpl : BaseTextUtilsImpl() {

    private val mediumTypefaceSpan = TypefaceSpan("sans-serif-medium")

    override fun getMediumSpan(): Any = mediumTypefaceSpan
}
