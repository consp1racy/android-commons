@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.app

import android.content.res.ColorStateList
import android.support.annotation.ColorInt
import java.util.*

inline fun @receiver:ColorInt Int.toColorStateList() = ColorStateList.valueOf(this)!!

inline fun <E> List<E>.asArrayList(): ArrayList<E> = this as? ArrayList<E> ?: ArrayList(this)
