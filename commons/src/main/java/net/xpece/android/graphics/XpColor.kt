@file:JvmName("XpColor")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.graphics

import android.content.res.ColorStateList
import androidx.annotation.ColorInt

@JvmSynthetic
inline fun @receiver:ColorInt Int.toColors(): ColorStateList = ColorStateList.valueOf(this)
