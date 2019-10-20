package net.xpece.android.edgeeffect.horizontalscrollview

import android.os.Build
import android.widget.HorizontalScrollView
import androidx.annotation.ColorInt

internal interface HorizontalScrollViewEdgeEffect {

    fun setEdgeEffectColor(scrollView: HorizontalScrollView, @ColorInt color: Int)

    fun setLeftEdgeEffectColor(scrollView: HorizontalScrollView, @ColorInt color: Int)

    fun setRightEdgeEffectColor(scrollView: HorizontalScrollView, @ColorInt color: Int)

    @ColorInt
    fun getLeftEdgeEffectColor(scrollView: HorizontalScrollView): Int

    @ColorInt
    fun getRightEdgeEffectColor(scrollView: HorizontalScrollView): Int

    companion object {

        val IMPL: HorizontalScrollViewEdgeEffect = when {
            Build.VERSION.SDK_INT >= 29 -> HorizontalScrollViewEdgeEffectApi29
            else -> HorizontalScrollViewEdgeEffectApi14
        }
    }
}
