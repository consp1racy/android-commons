package net.xpece.android.os

import android.os.Handler

@Suppress("NOTHING_TO_INLINE")
inline fun Handler.postDelayed(delayMillis: Long, noinline runnable: () -> Unit) = this.postDelayed(runnable, delayMillis)

@Suppress("NOTHING_TO_INLINE")
inline fun Handler.postDelayed(delayMillis: Int, noinline runnable: () -> Unit) = this.postDelayed(runnable, delayMillis.toLong())
