package net.xpece.android.edgeeffect.listview

import android.graphics.Color
import android.widget.AbsListView
import android.widget.EdgeEffect
import net.xpece.android.edgeeffect.widget.getColorCompat
import net.xpece.android.edgeeffect.widget.setColorCompat
import java.lang.reflect.Field

internal object AbsListViewEdgeEffectApi14 : AbsListViewEdgeEffect {

    private val fieldEdgeGlowTop: Field?
    private val fieldEdgeGlowBottom: Field?

    init {
        var edgeGlowTop: Field? = null
        var edgeGlowBottom: Field? = null

        for (f in AbsListView::class.java.declaredFields) {
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

    private fun getTopEdgeEffect(list: AbsListView): EdgeEffect? {
        return fieldEdgeGlowTop?.get(list) as EdgeEffect?
    }

    private fun getBottomEdgeEffect(list: AbsListView): EdgeEffect? {
        return fieldEdgeGlowBottom?.get(list) as EdgeEffect?
    }

    override fun setEdgeEffectColor(list: AbsListView, color: Int) {
        setTopEdgeEffectColor(list, color)
        setBottomEdgeEffectColor(list, color)
    }

    override fun setTopEdgeEffectColor(list: AbsListView, color: Int) {
        getTopEdgeEffect(list)?.setColorCompat(color)
    }

    override fun setBottomEdgeEffectColor(list: AbsListView, color: Int) {
        getBottomEdgeEffect(list)?.setColorCompat(color)
    }

    override fun getTopEdgeEffectColor(list: AbsListView): Int {
        return getTopEdgeEffect(list)?.getColorCompat() ?: Color.TRANSPARENT
    }

    override fun getBottomEdgeEffectColor(list: AbsListView): Int {
        return getBottomEdgeEffect(list)?.getColorCompat() ?: Color.TRANSPARENT
    }
}
