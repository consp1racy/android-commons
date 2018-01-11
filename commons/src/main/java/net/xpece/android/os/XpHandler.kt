package net.xpece.android.os

import android.os.Handler
import android.os.SystemClock

@JvmSynthetic
fun Handler.postDelayed(r: Runnable, delayMillis: Int) =
        postDelayed(r, delayMillis.toLong())

@JvmSynthetic
fun Handler.postDelayed(delayMillis: Int, runnable: () -> Unit) =
        this.postDelayed(runnable, delayMillis.toLong())

@JvmSynthetic
fun Handler.postDelayed(delayMillis: Long, runnable: () -> Unit) =
        this.postDelayed(runnable, delayMillis)

@JvmSynthetic
fun Handler.postDelayed(token: Any, delayMillis: Int, r: () -> Unit) =
        postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis)

@JvmSynthetic
fun Handler.postDelayed(token: Any, delayMillis: Long, r: () -> Unit) =
        postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis)

@JvmSynthetic
fun Handler.postDelayed(r: Runnable, token: Any, delayMillis: Int) =
        postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis)

fun Handler.postDelayed(r: Runnable, token: Any, delayMillis: Long) =
        postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis)
