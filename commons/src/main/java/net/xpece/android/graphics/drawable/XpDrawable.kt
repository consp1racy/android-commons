package net.xpece.android.graphics.drawable

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * @author Eugen
 */
private const val MAX_LEVEL = 10000
private const val MIN_LEVEL = 0

fun Drawable.reverse(duration: Int) {
    animate(duration, MAX_LEVEL, MIN_LEVEL)
}

@JvmOverloads fun Drawable.animate(duration: Int, from: Int = MIN_LEVEL, to: Int = MAX_LEVEL) {
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
fun Drawable.toBitmap(displayMetrics: DisplayMetrics?): Bitmap {
    val w = intrinsicWidth
    val h = intrinsicHeight
    val bitmap = if (Build.VERSION.SDK_INT > 17) {
        Bitmap.createBitmap(displayMetrics, w, h, Bitmap.Config.ARGB_8888)
    } else {
        val b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        if (displayMetrics != null) {
            b.density = displayMetrics.densityDpi
        }
        b
    }
    val canvas = Canvas(bitmap)
    setBounds(0, 0, w, h)
    draw(canvas)
    return bitmap
}
