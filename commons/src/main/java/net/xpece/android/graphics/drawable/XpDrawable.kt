@file:Suppress("unused")

package net.xpece.android.graphics.drawable

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.graphics.drawable.toBitmap as toBitmapImpl

/**
 * @author Eugen
 */
private const val MAX_LEVEL = 10000
private const val MIN_LEVEL = 0

fun Drawable.reverse(duration: Int) {
    animate(duration, MAX_LEVEL, MIN_LEVEL)
}

@JvmOverloads
fun Drawable.animate(duration: Int, from: Int = MIN_LEVEL, to: Int = MAX_LEVEL) {
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

fun Drawable.toBitmap(context: Context) = toBitmap(context.resources)
fun Drawable.toBitmap(resources: Resources) = toBitmap(resources.displayMetrics)
fun Drawable.toBitmap(displayMetrics: DisplayMetrics?): Bitmap =
    toBitmapImpl().apply { displayMetrics?.let { density = displayMetrics.densityDpi } }
