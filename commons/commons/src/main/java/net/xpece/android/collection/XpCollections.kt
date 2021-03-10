@file:JvmName("XpCollections")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.collection

import android.text.SpannableStringBuilder

@JvmOverloads
inline fun Iterable<CharSequence>.joinToCharSequence(
    separator: CharSequence = ", ",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    transformation: (CharSequence) -> CharSequence = { it }
): CharSequence {
    val b = SpannableStringBuilder()
    if (prefix.isNotEmpty()) b.append(prefix)
    forEachIndexed { i, it ->
        if (i > 0) b.append(separator)
        val x = transformation(it)
        b.append(x)
    }
    if (postfix.isNotEmpty()) b.append(postfix)
    return b
}
