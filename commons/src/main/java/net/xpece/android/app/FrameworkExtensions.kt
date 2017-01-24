package net.xpece.android.app

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import java.util.*

fun @receiver:ColorInt Int.toColorStateList() = ColorStateList.valueOf(this)!!

fun <E> List<E>.asArrayList(): ArrayList<E> {
    if (this is ArrayList<E>) {
        return this
    } else {
        return ArrayList(this)
    }
}

internal fun Boolean?.toByte(): Byte = when (this) {
    null -> -1
    true -> 1
    false -> 0
}

internal fun Byte.toBoolean(): Boolean? = if (this == 0.toByte()) {
    false
} else if (this > 0) {
    true
} else {
    null
}
