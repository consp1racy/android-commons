@file:JvmName("FrameworkExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.app

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import java.util.*
import net.xpece.android.collection.joinToCharSequence as joinToCharSequenceImpl

@Deprecated(
        message = "Moved.",
        replaceWith = ReplaceWith("toColors()", "net.xpece.android.graphics.toColors"),
        level = DeprecationLevel.ERROR
)
inline fun @receiver:ColorInt Int.toColorStateList() = ColorStateList.valueOf(this)

@Deprecated(
        message = "Moved.",
        replaceWith = ReplaceWith("asArrayList()", "net.xpece.java.collection.asArrayList"),
        level = DeprecationLevel.ERROR
)
inline fun <E> List<E>.asArrayList(): ArrayList<E> = this as? ArrayList<E> ?: ArrayList(this)

@Deprecated(
        message = "Moved.",
        replaceWith = ReplaceWith("joinToCharSequence(separator, prefix, postfix)", "net.xpece.android.collection.joinToCharSequence"),
        level = DeprecationLevel.ERROR
)
inline fun Iterable<CharSequence>.joinToCharSequence(
        separator: CharSequence = ", ",
        prefix: CharSequence = "",
        postfix: CharSequence = ""
): CharSequence = joinToCharSequenceImpl(separator, prefix, postfix)

@Deprecated(
        message = "Moved.",
        replaceWith = ReplaceWith("joinToCharSequence(separator, prefix, postfix, transformation)", "net.xpece.android.collection.joinToCharSequence"),
        level = DeprecationLevel.ERROR
)
inline fun Iterable<CharSequence>.joinToCharSequence(
        separator: CharSequence = ", ",
        prefix: CharSequence = "",
        postfix: CharSequence = "",
        transformation: (CharSequence) -> CharSequence
): CharSequence = joinToCharSequenceImpl(separator, prefix, postfix, transformation)

inline fun <T> T?.intIfNotNull(number: T.() -> Int): Int = this?.number() ?: 0
inline fun <T> T?.intIfNotNull(number: Int): Int = if (this != null) number else 0
inline fun <T> T?.oneIfNotNull(): Int = intIfNotNull(1)

inline fun equalsNotNull(a: Any?, b: Any?) = a != null && a == b
