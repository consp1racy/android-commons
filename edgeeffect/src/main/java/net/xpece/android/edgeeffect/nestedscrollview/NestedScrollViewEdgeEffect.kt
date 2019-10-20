package net.xpece.android.edgeeffect.nestedscrollview

import android.graphics.Color
import android.widget.EdgeEffect
import androidx.annotation.ColorInt
import androidx.core.widget.NestedScrollView
import net.xpece.android.edgeeffect.widget.getColorCompat
import net.xpece.android.edgeeffect.widget.setColorCompat
import java.lang.reflect.Field

internal object NestedScrollViewEdgeEffect {

    private val fieldEdgeGlowTop: Field?
    private val fieldEdgeGlowBottom: Field?

    init {
        var edgeGlowTop: Field? = null
        var edgeGlowBottom: Field? = null

        for (f in NestedScrollView::class.java.declaredFields) {
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

    private fun getTopEdgeEffect(scrollView: NestedScrollView): EdgeEffect? {
        return fieldEdgeGlowTop?.get(scrollView) as EdgeEffect?
    }

    private fun getBottomEdgeEffect(scrollView: NestedScrollView): EdgeEffect? {
        return fieldEdgeGlowBottom?.get(scrollView) as EdgeEffect?
    }

    fun setEdgeEffectColor(scrollView: NestedScrollView, @ColorInt color: Int) {
        setTopEdgeEffectColor(scrollView, color)
        setBottomEdgeEffectColor(scrollView, color)
    }

    fun setTopEdgeEffectColor(scrollView: NestedScrollView, @ColorInt color: Int) {
        getTopEdgeEffect(scrollView)?.setColorCompat(color)
    }

    fun setBottomEdgeEffectColor(scrollView: NestedScrollView, @ColorInt color: Int) {
        getBottomEdgeEffect(scrollView)?.setColorCompat(color)
    }

    @ColorInt
    fun getTopEdgeEffectColor(scrollView: NestedScrollView): Int {
        return getTopEdgeEffect(scrollView)?.getColorCompat() ?: Color.TRANSPARENT
    }

    @ColorInt
    fun getBottomEdgeEffectColor(scrollView: NestedScrollView): Int {
        return getBottomEdgeEffect(scrollView)?.getColorCompat() ?: Color.TRANSPARENT
    }
}
