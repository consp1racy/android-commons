@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.app

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import net.xpece.android.text.Truss
import java.util.*

inline fun @receiver:ColorInt Int.toColorStateList() = ColorStateList.valueOf(this)!!

inline fun <E> List<E>.asArrayList(): ArrayList<E> = this as? ArrayList<E> ?: ArrayList(this)

inline fun Iterable<CharSequence>.joinToCharSequence(
        separator: CharSequence = ", ",
        prefix: CharSequence = "",
        postfix: CharSequence = ""): CharSequence =
        joinToCharSequence(separator, prefix, postfix, { it })

inline fun Iterable<CharSequence>.joinToCharSequence(
        separator: CharSequence = ", ",
        prefix: CharSequence = "",
        postfix: CharSequence = "",
        transformation: (CharSequence) -> CharSequence): CharSequence {
    val t = Truss().append(prefix)
    forEachIndexed { i, it ->
        if (i > 0) t.append(separator)
        val x = transformation(it)
        t.append(x)
    }
    return t.append(postfix).build()
}

inline fun <T> T?.intIfNotNull(number: T.() -> Int): Int = this?.number() ?: 0
inline fun <T> T?.intIfNotNull(number: Int): Int = if (this != null) number else 0
inline fun <T> T?.oneIfNotNull(): Int = intIfNotNull(1)

inline fun equalsNotNull(a: Any?, b: Any?) = a != null && a == b
