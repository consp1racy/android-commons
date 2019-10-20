package net.xpece.android.edgeeffect.widget

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.EdgeEffect
import androidx.core.graphics.drawable.DrawableCompat
import java.lang.reflect.Field

internal object ColorEdgeEffectApi14 : ColorEdgeEffect {

    private var isResolvedEdgeEffectClass = false

    private var fieldEdge: Field? = null
    private var fieldGlow: Field? = null

    private fun resolveEdgeEffectFields(edgeEffect: Any) {
        if (!isResolvedEdgeEffectClass) {
            isResolvedEdgeEffectClass = true

            try {
                val klazz = edgeEffect.javaClass
                fieldEdge = klazz.getDeclaredField("mEdge").apply { isAccessible = true }
                fieldGlow = klazz.getDeclaredField("mGlow").apply { isAccessible = true }
            } catch (ignore: Throwable) {
                fieldEdge = null
                fieldGlow = null
            }
        }
    }

    private fun getWrappedDrawable(field: Field, edgeEffect: Any): Drawable? {
        val original = field.get(edgeEffect) as Drawable? ?: return null
        val wrapped = DrawableCompat.wrap(original)
        if (wrapped === original) return original

        field.set(edgeEffect, wrapped)
        return wrapped
    }

    private fun getEdgeDrawable(edgeEffect: Any): Drawable? {
        return fieldEdge?.let { getWrappedDrawable(it, edgeEffect) }
    }

    private fun getGlowDrawable(edgeEffect: Any): Drawable? {
        return fieldGlow?.let { getWrappedDrawable(it, edgeEffect) }
    }

    override fun setColor(edgeEffect: EdgeEffect, color: Int) {
        resolveEdgeEffectFields(edgeEffect)

        getEdgeDrawable(edgeEffect)?.let { DrawableCompat.setTint(it, color) }
        getGlowDrawable(edgeEffect)?.let { DrawableCompat.setTint(it, color) }
    }

    override fun getColor(edgeEffect: EdgeEffect): Int {
        return Color.TRANSPARENT
    }
}
