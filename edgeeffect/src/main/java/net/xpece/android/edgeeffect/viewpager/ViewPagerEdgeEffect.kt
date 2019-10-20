package net.xpece.android.edgeeffect.viewpager

import android.graphics.Color
import android.widget.EdgeEffect
import androidx.annotation.ColorInt
import androidx.viewpager.widget.ViewPager
import net.xpece.android.edgeeffect.widget.getColorCompat
import net.xpece.android.edgeeffect.widget.setColorCompat
import java.lang.reflect.Field

internal object ViewPagerEdgeEffect {

    private val fieldLeftEdge: Field?
    private val fieldRightEdge: Field?

    init {
        var leftEdge: Field? = null
        var rightEdge: Field? = null

        for (f in ViewPager::class.java.declaredFields) {
            when (f.name) {
                "mLeftEdge" -> {
                    f.isAccessible = true
                    leftEdge = f
                }
                "mRightEdge" -> {
                    f.isAccessible = true
                    rightEdge = f
                }
            }
        }

        fieldLeftEdge = leftEdge
        fieldRightEdge = rightEdge
    }

    private fun getLeftEdgeEffect(viewPager: ViewPager): EdgeEffect? {
        return fieldLeftEdge?.get(viewPager) as EdgeEffect?
    }

    private fun getRightEdgeEffect(viewPager: ViewPager): EdgeEffect? {
        return fieldRightEdge?.get(viewPager) as EdgeEffect?
    }

    fun setEdgeEffectColor(viewPager: ViewPager, @ColorInt color: Int) {
        setLeftEdgeEffectColor(viewPager, color)
        setRightEdgeEffectColor(viewPager, color)
    }

    fun setLeftEdgeEffectColor(viewPager: ViewPager, @ColorInt color: Int) {
        getLeftEdgeEffect(viewPager)?.setColorCompat(color)
    }

    fun setRightEdgeEffectColor(viewPager: ViewPager, @ColorInt color: Int) {
        getRightEdgeEffect(viewPager)?.setColorCompat(color)
    }

    @ColorInt
    fun getLeftEdgeEffectColor(viewPager: ViewPager): Int {
        return getLeftEdgeEffect(viewPager)?.getColorCompat() ?: Color.TRANSPARENT
    }

    @ColorInt
    fun getRightEdgeEffectColor(viewPager: ViewPager): Int {
        return getRightEdgeEffect(viewPager)?.getColorCompat() ?: Color.TRANSPARENT
    }
}
