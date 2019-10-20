package net.xpece.android.edgeeffect.widget

import android.os.Build
import android.widget.EdgeEffect
import androidx.annotation.ColorInt

internal interface ColorEdgeEffect {

    fun setColor(edgeEffect: EdgeEffect, @ColorInt color: Int)

    @ColorInt
    fun getColor(edgeEffect: EdgeEffect): Int

    companion object {

        val IMPL = when {
            Build.VERSION.SDK_INT >= 21 -> ColorEdgeEffectApi21
            else -> ColorEdgeEffectApi14
        }
    }
}
