package net.xpece.android.content.res

import android.content.Context

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
}
