@file:JvmName("XpDrawable")

package net.xpece.android.graphics.drawable

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.view.animation.AccelerateDecelerateInterpolator

private const val MAX_LEVEL = 10000
private const val MIN_LEVEL = 0

internal fun Drawable.reverse(duration: Int) {
    animate(duration, MAX_LEVEL, MIN_LEVEL)
}

@JvmOverloads
internal fun Drawable.animate(duration: Int, from: Int = MIN_LEVEL, to: Int = MAX_LEVEL) {
    val a = ValueAnimator.ofInt(from, to)
    a.duration = duration.toLong()
    a.interpolator = AccelerateDecelerateInterpolator()
    a.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
            level = from
        }

        override fun onAnimationCancel(animation: Animator) {
            level = to
        }

        override fun onAnimationEnd(animation: Animator) {
            level = to
        }
    })
    a.addUpdateListener { animation -> level = animation.animatedValue as Int }
    a.start()
}
