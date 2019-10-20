package net.xpece.android.edgeeffect.listview

import android.widget.AbsListView
import androidx.annotation.RequiresApi

@RequiresApi(29)
internal object AbsListViewEdgeEffectApi29 : AbsListViewEdgeEffect {

    override fun setEdgeEffectColor(list: AbsListView, color: Int) {
        list.setEdgeEffectColor(color)
    }

    override fun setTopEdgeEffectColor(list: AbsListView, color: Int) {
        list.topEdgeEffectColor = color
    }

    override fun setBottomEdgeEffectColor(list: AbsListView, color: Int) {
        list.bottomEdgeEffectColor = color
    }

    override fun getTopEdgeEffectColor(list: AbsListView): Int {
        return list.topEdgeEffectColor
    }

    override fun getBottomEdgeEffectColor(list: AbsListView): Int {
        return list.bottomEdgeEffectColor
    }
}
