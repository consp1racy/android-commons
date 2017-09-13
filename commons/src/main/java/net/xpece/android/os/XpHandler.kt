package net.xpece.android.os

import android.os.Handler
import android.os.SystemClock

fun Handler.postDelayed(r: Runnable, delayMillis: Int) =
        postDelayed(r, delayMillis.toLong())

fun Handler.postDelayed(delayMillis: Int, runnable: () -> Unit) =
        this.postDelayed(runnable, delayMillis.toLong())

fun Handler.postDelayed(token: Any, delayMillis: Int, r: () -> Unit) =
        postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis)

fun Handler.postDelayed(r: Runnable, token: Any, delayMillis: Int) =
        postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis)
