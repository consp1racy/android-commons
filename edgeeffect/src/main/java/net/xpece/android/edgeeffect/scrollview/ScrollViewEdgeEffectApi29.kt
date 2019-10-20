package net.xpece.android.edgeeffect.scrollview

import android.widget.ScrollView
import androidx.annotation.RequiresApi

@RequiresApi(29)
internal object ScrollViewEdgeEffectApi29 : ScrollViewEdgeEffect {

    override fun setEdgeEffectColor(scrollView: ScrollView, color: Int) {
        scrollView.setEdgeEffectColor(color)
    }

    override fun setTopEdgeEffectColor(scrollView: ScrollView, color: Int) {
        scrollView.topEdgeEffectColor = color
    }

    override fun setBottomEdgeEffectColor(scrollView: ScrollView, color: Int) {
        scrollView.bottomEdgeEffectColor = color
    }

    override fun getTopEdgeEffectColor(scrollView: ScrollView): Int {
        return scrollView.topEdgeEffectColor
    }

    override fun getBottomEdgeEffectColor(scrollView: ScrollView): Int {
        return scrollView.bottomEdgeEffectColor
    }
}
