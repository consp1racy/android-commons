package net.xpece.android.edgeeffect.horizontalscrollview

import android.widget.HorizontalScrollView
import androidx.annotation.RequiresApi

@RequiresApi(29)
internal object HorizontalScrollViewEdgeEffectApi29 : HorizontalScrollViewEdgeEffect {

    override fun setEdgeEffectColor(scrollView: HorizontalScrollView, color: Int) {
        scrollView.setEdgeEffectColor(color)
    }

    override fun setLeftEdgeEffectColor(scrollView: HorizontalScrollView, color: Int) {
        scrollView.leftEdgeEffectColor = color
    }

    override fun setRightEdgeEffectColor(scrollView: HorizontalScrollView, color: Int) {
        scrollView.rightEdgeEffectColor = color
    }

    override fun getLeftEdgeEffectColor(scrollView: HorizontalScrollView): Int {
        return scrollView.leftEdgeEffectColor
    }

    override fun getRightEdgeEffectColor(scrollView: HorizontalScrollView): Int {
        return scrollView.rightEdgeEffectColor
    }
}
