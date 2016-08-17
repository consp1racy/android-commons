@file:JvmName("XpText")

package net.xpece.android.text

import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Eugen on 30.07.2016.
 */

private val PATTERN_UNACCENT = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")

/**
 * Source: http://www.rgagnon.com/javadetails/java-0456.html
 */
fun CharSequence.unaccent(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return PATTERN_UNACCENT.matcher(temp).replaceAll("")
}

fun CharSequence.unaccentAndLower(locale: Locale = Locale.getDefault()): String {
    return this.unaccent().toLowerCase(locale)
}

/**
 * @param subject Original string with accents
 * @param unEmp Constraint. Unready unaccented for increased effectivity.
 * @return Formatted original string.
 */
@Suppress("deprecation")
fun emphasize(subject: String, unEmp: String): CharSequence {
    val unSub = subject.unaccentAndLower()
    if (unSub == unEmp) {
        return Html.fromHtml("<b>$subject</b>")
    }

    val lenTotal = subject.length
    val lenEmp = unEmp.length
    val unParts = unSub.split(unEmp.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val sb = StringBuilder()
    var position = 0
    for (unPart in unParts) {
        if (position >= lenTotal) continue
        // emphasize only first letters of words
        // this behavior reflects that of the filter
        val len = unPart.length
        val original = subject.substring(position, position + len)
        position += len
        sb.append(original)
        if (position >= lenTotal) continue
        val emp = subject.substring(position, position + lenEmp)
        if (position == 0 || unSub.substring(position - 1, position).matches("[^\\w]".toRegex())) {
            sb.append("<b>").append(emp).append("</b>")
        } else {
            sb.append(emp)
        }
        position += lenEmp
    }
    return Html.fromHtml(sb.toString())
}

private val IMPL: TextUtilsImpl by lazy {
    if (Build.VERSION.SDK_INT >= 21) {
        LollipopTextUtilsImpl()
    } else {
        BaseTextUtilsImpl()
    }
}

fun getMediumSpan(): Any {
    return IMPL.getMediumSpan()
}

internal interface TextUtilsImpl {
    fun getMediumSpan(): Any
}

internal open class BaseTextUtilsImpl : TextUtilsImpl {
    override fun getMediumSpan(): Any {
        return StyleSpan(Typeface.BOLD)
    }
}

internal open class LollipopTextUtilsImpl() : BaseTextUtilsImpl() {
    override fun getMediumSpan(): Any {
        return TypefaceSpan("sans-serif-medium")
    }
}
