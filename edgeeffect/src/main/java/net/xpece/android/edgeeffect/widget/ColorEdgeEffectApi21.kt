package net.xpece.android.edgeeffect.widget

import android.widget.EdgeEffect
import androidx.annotation.RequiresApi

@RequiresApi(21)
internal object ColorEdgeEffectApi21 : ColorEdgeEffect {

    override fun setColor(edgeEffect: EdgeEffect, color: Int) {
        edgeEffect.color = color
    }

    override fun getColor(edgeEffect: EdgeEffect): Int {
        return edgeEffect.color
    }
}
