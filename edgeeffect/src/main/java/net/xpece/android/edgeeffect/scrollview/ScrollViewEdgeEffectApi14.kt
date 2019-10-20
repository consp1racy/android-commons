package net.xpece.android.edgeeffect.scrollview

import android.graphics.Color
import android.widget.EdgeEffect
import android.widget.ScrollView
import net.xpece.android.edgeeffect.widget.getColorCompat
import net.xpece.android.edgeeffect.widget.setColorCompat
import java.lang.reflect.Field

internal object ScrollViewEdgeEffectApi14 : ScrollViewEdgeEffect {

    private val fieldEdgeGlowTop: Field?
    private val fieldEdgeGlowBottom: Field?

    init {
        var edgeGlowTop: Field? = null
        var edgeGlowBottom: Field? = null

        for (f in ScrollView::class.java.declaredFields) {
            when (f.name) {
                "mEdgeGlowTop" -> {
                    f.isAccessible = true
                    edgeGlowTop = f
                }
                "mEdgeGlowBottom" -> {
                    f.isAccessible = true
                    edgeGlowBottom = f
                }
            }
        }

        fieldEdgeGlowTop = edgeGlowTop
        fieldEdgeGlowBottom = edgeGlowBottom
    }

    private fun getTopEdgeEffect(scrollView: ScrollView): EdgeEffect? {
        return fieldEdgeGlowTop?.get(scrollView) as EdgeEffect?
    }

    private fun getBottomEdgeEffect(scrollView: ScrollView): EdgeEffect? {
        return fieldEdgeGlowBottom?.get(scrollView) as EdgeEffect?
    }

    override fun setEdgeEffectColor(scrollView: ScrollView, color: Int) {
        setTopEdgeEffectColor(scrollView, color)
        setBottomEdgeEffectColor(scrollView, color)
    }

    override fun setTopEdgeEffectColor(scrollView: ScrollView, color: Int) {
        getTopEdgeEffect(scrollView)?.setColorCompat(color)
    }

    override fun setBottomEdgeEffectColor(scrollView: ScrollView, color: Int) {
        getBottomEdgeEffect(scrollView)?.setColorCompat(color)
    }

    override fun getTopEdgeEffectColor(scrollView: ScrollView): Int {
        return getTopEdgeEffect(scrollView)?.getColorCompat() ?: Color.TRANSPARENT
    }

    override fun getBottomEdgeEffectColor(scrollView: ScrollView): Int {
        return getBottomEdgeEffect(scrollView)?.getColorCompat() ?: Color.TRANSPARENT
    }
}
