package net.xpece.android.edgeeffect.widget

import android.widget.EdgeEffect
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

class ColorEdgeEffectFactory(
        @ColorInt private val leftColor: Int,
        @ColorInt private val topColor: Int,
        @ColorInt private val rightColor: Int,
        @ColorInt private val bottomColor: Int
) : RecyclerView.EdgeEffectFactory() {

    constructor(
            @ColorInt horizontalColor: Int,
            @ColorInt verticalColor: Int
    ) : this(horizontalColor, verticalColor, horizontalColor, verticalColor)

    constructor(@ColorInt color: Int) : this(color, color, color, color)

    override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
        val edgeEffect = super.createEdgeEffect(view, direction)
        val color = when(direction) {
            DIRECTION_LEFT -> leftColor
            DIRECTION_TOP -> topColor
            DIRECTION_RIGHT -> rightColor
            DIRECTION_BOTTOM -> bottomColor
            else -> leftColor
        }
        edgeEffect.setColorCompat(color)
        return edgeEffect
    }
}
