package net.xpece.android.app

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import java.util.*

inline fun @receiver:ColorInt Int.toColorStateList() = ColorStateList.valueOf(this)!!

fun <E> List<E>.asArrayList(): ArrayList<E> {
    if (this is ArrayList<E>) {
        return this
    } else {
        return ArrayList(this)
    }
}
