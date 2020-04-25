@file:JvmName("XpWindow")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.view

import android.os.Build.VERSION.SDK_INT
import android.view.Window
import androidx.annotation.ColorInt

inline var Window.statusBarColorCompat: Int
    @ColorInt get() = if (SDK_INT >= 21) statusBarColor else 0
    set(@ColorInt value) {
        if (SDK_INT >= 21) statusBarColor = value
    }

inline var Window.navigationBarColorCompat: Int
    @ColorInt get() = if (SDK_INT >= 21) navigationBarColor else 0
    set(@ColorInt value) {
        if (SDK_INT >= 21) navigationBarColor = value
    }
