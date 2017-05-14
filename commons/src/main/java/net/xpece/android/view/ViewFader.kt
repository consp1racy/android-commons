package net.xpece.android.view

import android.support.annotation.RequiresApi
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.View.VISIBLE
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import java.util.*

@RequiresApi(16)
class ViewFader {
    companion object {
        private val HIDE_INTERPOLATOR = AccelerateInterpolator()
        private val SHOW_INTERPOLATOR = DecelerateInterpolator()

        private const val FADE_OUT_DURATION_MILLIS = 200L
        private const val FADE_IN_DURATION_MILLIS = 100L

        private const val SHOWING = 0
        private const val HIDING = 1
    }

    private val fadingViews = WeakHashMap<View, Int>()

    fun isFadingIn(view: View) = fadingViews[view] == SHOWING
    fun isFadingOut(view: View) = fadingViews[view] == HIDING

    fun fadeOut(view: View): Unit = view.run {
        if (visibility == VISIBLE && !isFadingOut(this)) {
            clearAnimation()
            if (ViewCompat.isLaidOut(this)) {
                animate()
                        .alpha(0F)
                        .setDuration(FADE_OUT_DURATION_MILLIS)
                        .setInterpolator(HIDE_INTERPOLATOR)
                        .withStartAction { fadingViews[this] = HIDING }
                        .withEndAction { invisible(); fadingViews.remove(this) }
                        .start()
            } else {
                invisible()
                alpha = 0F
            }
        }
    }


    fun fadeIn(view: View): Unit = view.run {
        if (visibility != VISIBLE && !isFadingIn(this)) {
            clearAnimation()
            if (ViewCompat.isLaidOut(this)) {
                animate()
                        .alpha(1F)
                        .setDuration(FADE_IN_DURATION_MILLIS)
                        .setInterpolator(SHOW_INTERPOLATOR)
                        .withStartAction { fadingViews[this] = SHOWING; visible() }
                        .withEndAction { fadingViews.remove(this) }
                        .start()
            } else {
                alpha = 1F
                visible()
            }
        }
    }
}
