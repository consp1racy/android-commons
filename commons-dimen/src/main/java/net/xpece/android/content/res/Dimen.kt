package net.xpece.android.content.res

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.support.annotation.AttrRes
import android.support.annotation.DimenRes
import android.util.TypedValue
import net.xpece.android.content.XpInitProvider
import net.xpece.android.content.resolveResourceId

/**
 * Created by pechanecjr on 4. 1. 2015.
 */
data class Dimen internal constructor(val value: Float) : Comparable<Dimen> {
    override fun compareTo(other: Dimen): Int = this.value.compareTo(other.value)

    operator fun plus(that: Dimen): Dimen = Dimen(
            this.value + that.value)

    operator fun minus(that: Dimen): Dimen = Dimen(
            this.value - that.value)

    operator fun plus(that: Number): Dimen = Dimen(
            this.value + that.toFloat())

    operator fun minus(that: Number): Dimen = Dimen(
            this.value - that.toFloat())

    operator fun unaryMinus() = Dimen(-this.value)

    operator fun unaryPlus() = Dimen(this.value)

    operator fun times(q: Number) = Dimen(this.value * q.toFloat())

    operator fun div(d: Number) = Dimen(this.value / d.toFloat())

    operator fun div(d: Dimen) = this.value / d.value

    val pixelSize: Int
        get() {
            val res = (value + 0.5F).toInt()
            if (res != 0) return res
            if (value == 0F) return 0
            if (value > 0) return 1
            return -1
        }

    val pixelOffset: Int
        get() = value.toInt()

    override fun toString(): String = "Dimen(${value}px)"

    /**
     * Along px prints also dp and sp values according to provided context.
     */
    fun toString(context: Context): String {
        val density = context.resources.displayMetrics.density
        val dp = Math.round(value / density)
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        val sp = Math.round(value / scaledDensity)
        return "Dimen(${value}px ~ ${dp}dp ~ ${sp}sp)"
    }

    /**
     * Internal companion class is not accessible from outside of package in Kotlin but in Java
     * it's merged into the public parent class. Brilliant!
     */
    internal companion object {
        @SuppressLint("StaticFieldLeak")
        private var sContext: Context = XpInitProvider.sContext.applicationContext

        @JvmStatic
        fun init(context: Context) {
            sContext = context.applicationContext
        }

        @JvmStatic
        fun attr(context: Context, @AttrRes attr: Int, fallback: Number = 0): Dimen {
            val resId = context.resolveResourceId(attr, 0)
            return res(context, resId, fallback)
        }

        @JvmStatic
        fun res(context: Context, @DimenRes resId: Int, fallback: Number = 0): Dimen = try {
            Dimen(context.resources.getDimension(resId))
        } catch (erx: Resources.NotFoundException) {
            Dimen(fallback.toFloat())
        }

        @JvmStatic
        fun dp(context: Context, dp: Number): Dimen {
            val dpf = dp.toFloat()
            val value = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, dpf, context.resources.displayMetrics)
            return Dimen(value)
        }

        @JvmStatic
        fun sp(context: Context, sp: Number): Dimen {
            val spf = sp.toFloat()
            val value = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP, spf, context.resources.displayMetrics)
            return Dimen(value)
        }

        @JvmStatic
        fun res(@DimenRes resId: Int): Dimen = res(
                sContext, resId)

        @JvmStatic
        fun dp(dp: Number): Dimen = dp(
                sContext, dp)

        @JvmStatic
        fun sp(sp: Number): Dimen = sp(
                sContext, sp)

        @JvmStatic
        fun px(px: Number): Dimen = Dimen(
                px.toFloat())
    }
}
