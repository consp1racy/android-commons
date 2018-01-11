@file:JvmName("XpWindow")

package net.xpece.android.view

import android.annotation.TargetApi
import android.os.Build
import android.support.annotation.ColorInt
import android.view.Window

@ColorInt
@JvmField
val DEFAULT_SCRIM = 0x44000000

@TargetApi(21)
inline fun Window.setNavigationBarColorCompat(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= 21) {
        navigationBarColor = color
    }
}

@TargetApi(21)
inline fun Window.setStatusBarColorCompat(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= 21) {
        statusBarColor = color
    }
}
