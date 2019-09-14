@file:JvmName("XpWindow")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.view

import android.annotation.TargetApi
import android.os.Build
import android.view.Window
import androidx.annotation.ColorInt

@ColorInt
@JvmField
val DEFAULT_SCRIM = 0x44000000

@TargetApi(21)
inline fun Window.setNavigationBarColorCompat(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= 21) navigationBarColor = color
}

@TargetApi(21)
inline fun Window.setStatusBarColorCompat(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= 21) statusBarColor = color
}
