package net.xpece.android.edgeeffect.scrollview

import android.os.Build
import android.widget.ScrollView
import androidx.annotation.ColorInt

internal interface ScrollViewEdgeEffect {

    fun setEdgeEffectColor(scrollView: ScrollView, @ColorInt color: Int)

    fun setTopEdgeEffectColor(scrollView: ScrollView, @ColorInt color: Int)

    fun setBottomEdgeEffectColor(scrollView: ScrollView, @ColorInt color: Int)

    @ColorInt
    fun getTopEdgeEffectColor(scrollView: ScrollView): Int

    @ColorInt
    fun getBottomEdgeEffectColor(scrollView: ScrollView): Int

    companion object {

        val IMPL: ScrollViewEdgeEffect = when {
            Build.VERSION.SDK_INT >= 29 -> ScrollViewEdgeEffectApi29
            else -> ScrollViewEdgeEffectApi14
        }
    }
}
