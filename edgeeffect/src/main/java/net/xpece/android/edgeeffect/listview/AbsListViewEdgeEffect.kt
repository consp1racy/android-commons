package net.xpece.android.edgeeffect.listview

import android.os.Build
import android.widget.AbsListView
import androidx.annotation.ColorInt

internal interface AbsListViewEdgeEffect {

    fun setEdgeEffectColor(list: AbsListView, @ColorInt color: Int)

    fun setTopEdgeEffectColor(list: AbsListView, @ColorInt color: Int)

    fun setBottomEdgeEffectColor(list: AbsListView, @ColorInt color: Int)

    @ColorInt
    fun getTopEdgeEffectColor(list: AbsListView): Int

    @ColorInt
    fun getBottomEdgeEffectColor(list: AbsListView): Int

    companion object {

        val IMPL: AbsListViewEdgeEffect = when {
            Build.VERSION.SDK_INT >= 29 -> AbsListViewEdgeEffectApi29
            else -> AbsListViewEdgeEffectApi14
        }
    }
}
