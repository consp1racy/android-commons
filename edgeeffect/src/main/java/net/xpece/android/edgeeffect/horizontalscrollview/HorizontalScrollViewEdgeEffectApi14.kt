package net.xpece.android.edgeeffect.horizontalscrollview

import android.graphics.Color
import android.widget.EdgeEffect
import android.widget.HorizontalScrollView
import net.xpece.android.edgeeffect.widget.getColorCompat
import net.xpece.android.edgeeffect.widget.setColorCompat
import java.lang.reflect.Field

internal object HorizontalScrollViewEdgeEffectApi14 : HorizontalScrollViewEdgeEffect {

    private val fieldEdgeGlowLeft: Field?
    private val fieldEdgeGlowRight: Field?

    init {
        var edgeGlowLeft: Field? = null
        var edgeGlowRight: Field? = null

        for (f in HorizontalScrollView::class.java.declaredFields) {
            when (f.name) {
                "mEdgeGlowLeft" -> {
                    f.isAccessible = true
                    edgeGlowLeft = f
                }
                "mEdgeGlowRight" -> {
                    f.isAccessible = true
                    edgeGlowRight = f
                }
            }
        }

        fieldEdgeGlowLeft = edgeGlowLeft
        fieldEdgeGlowRight = edgeGlowRight
    }

    private fun getLeftEdgeEffect(scrollView: HorizontalScrollView): EdgeEffect? {
        return fieldEdgeGlowLeft?.get(scrollView) as EdgeEffect?
    }

    private fun getRightEdgeEffect(scrollView: HorizontalScrollView): EdgeEffect? {
        return fieldEdgeGlowRight?.get(scrollView) as EdgeEffect?
    }

    override fun setEdgeEffectColor(scrollView: HorizontalScrollView, color: Int) {
        setLeftEdgeEffectColor(scrollView, color)
        setRightEdgeEffectColor(scrollView, color)
    }

    override fun setLeftEdgeEffectColor(scrollView: HorizontalScrollView, color: Int) {
        getLeftEdgeEffect(scrollView)?.setColorCompat(color)
    }

    override fun setRightEdgeEffectColor(scrollView: HorizontalScrollView, color: Int) {
        getRightEdgeEffect(scrollView)?.setColorCompat(color)
    }

    override fun getLeftEdgeEffectColor(scrollView: HorizontalScrollView): Int {
        return getLeftEdgeEffect(scrollView)?.getColorCompat() ?: Color.TRANSPARENT
    }

    override fun getRightEdgeEffectColor(scrollView: HorizontalScrollView): Int {
        return getRightEdgeEffect(scrollView)?.getColorCompat() ?: Color.TRANSPARENT
    }
}
