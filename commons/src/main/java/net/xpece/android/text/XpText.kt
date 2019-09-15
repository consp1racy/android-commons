@file:JvmName("XpText")

package net.xpece.android.text

import android.graphics.Typeface
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import androidx.collection.LruCache
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern

private val PATTERN_UNACCENT = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")

/**
 * Source: http://www.rgagnon.com/javadetails/java-0456.html
 */
fun CharSequence.removeDiacritics(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return PATTERN_UNACCENT.matcher(temp).replaceAll("")
}

// Emphasis ========================================================================================

private val BOLD = StyleSpan(Typeface.BOLD)

// We sacrifice memory for speed.
// Assume we have at most 20 texts on screen
private val subjects = LruCache<String, String>(20)
private val emphases = LruCache<String, String>(20)
private var lastLocale = Locale.getDefault()

object EmphasisCache {

    /**
     * How many items on screen are tested at most. Default is 20.
     */
    fun resizeSubjectCache(maxSize: Int) {
        subjects.resize(maxSize)
    }

    /**
     * How many characters back and forth is the user going to type in the filter. Default is 20.
     */
    fun resizeEmphasesCache(maxSize: Int) {
        subjects.resize(maxSize)
    }

    /**
     * You may call this after finishing filtering.
     */
    fun clearCache() {
        subjects.evictAll()
        emphases.evictAll()
    }
}

fun emphasize(
        text: String,
        emphasis: CharSequence,
        locale: Locale = Locale.getDefault()
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

    val sb = SpannableString(text)

    emphasize(sb, text, emphasis.toString(), locale)

    return sb
}

private fun emphasize(
        sb: SpannableString,
        text: String,
        emphasis: String,
        locale: Locale
) {
    if (lastLocale != locale) {
        lastLocale = locale
        EmphasisCache.clearCache()
    }

    @Suppress("NAME_SHADOWING")
    val text = subjects[text]
            ?: text.removeDiacritics().toLowerCase(locale)
                    .also { subjects.put(text, it) }
    @Suppress("NAME_SHADOWING")
    val emphasis = emphases[emphasis]
            ?: emphasis.removeDiacritics().toLowerCase(locale)
                    .also { emphases.put(emphasis, it) }

    if (text == emphasis) {
        // We have a perfect match!
        sb.setSpan(BOLD, 0, sb.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    } else {
        val lenEmp = emphasis.length
        var next = text.indexOf(emphasis)
        while (next != -1) {
            // emphasize only first letters of words
            // this behavior reflects that of the filter
            if (next == 0 || text[next - 1].isWhitespace()) {
                sb.setSpan(BOLD, next, next + lenEmp, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            next = text.indexOf(emphasis, next + lenEmp)
        }
    }
}

// Medium Span =====================================================================================

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
