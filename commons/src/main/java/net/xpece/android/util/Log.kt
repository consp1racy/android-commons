@file:JvmName("XpLog")

package net.xpece.android.util

import android.content.Context
import android.content.pm.ApplicationInfo
import android.view.View

fun Context.logException(ex: Throwable) {
    val debug = applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    if (debug) ex.printStackTrace()
}

fun View.logException(ex: Throwable) {
    context.logException(ex)
}
