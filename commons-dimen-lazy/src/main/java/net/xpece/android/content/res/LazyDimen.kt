package net.xpece.android.content.res

import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.DimenRes
import net.xpece.android.content.*

sealed class LazyDimen {
    abstract fun resolve(context: Context): Dimen

    operator fun plus(other: LazyDimen): LazyDimen = Add(this, other)
    operator fun minus(other: LazyDimen): LazyDimen = Subtract(this, other)
    operator fun times(other: Number): LazyDimen = Multiply(this, other)
    operator fun div(other: Number): LazyDimen = Divide(this, other)

    @Deprecated("Supported but inefficient.")
    operator fun plus(other: Dimen): LazyDimen = Add(this, Px(other.value))
    @Deprecated("Supported but inefficient.")
    operator fun minus(other: Dimen): LazyDimen = Subtract(this, Px(other.value))

    @Deprecated("Supported but inefficient.")
    operator fun unaryMinus(): LazyDimen = Multiply(this, -1)
    @Deprecated("Supported but inefficient.")
    operator fun unaryPlus(): LazyDimen = Multiply(this, 1)

    internal data class Add(val first: LazyDimen, val second: LazyDimen) : LazyDimen() {
        override fun resolve(context: Context) = first.resolve(context) + second.resolve(context)
    }

    internal data class Subtract(val first: LazyDimen, val second: LazyDimen) : LazyDimen() {
        override fun resolve(context: Context) = first.resolve(context) - second.resolve(context)
    }

    internal data class Multiply(val first: LazyDimen, val second: Number) : LazyDimen() {
        override fun resolve(context: Context) = first.resolve(context) * second
    }

    internal data class Divide(val first: LazyDimen, val second: Number) : LazyDimen() {
        override fun resolve(context: Context) = first.resolve(context) / second
    }

    internal data class Px(val value: Number) : LazyDimen() {
        override fun resolve(context: Context): Dimen = px(value)
    }

    internal data class Dp(val value: Number) : LazyDimen() {
        override fun resolve(context: Context): Dimen = context.dp(value)
    }

    internal data class Sp(val value: Number) : LazyDimen() {
        override fun resolve(context: Context): Dimen = context.sp(value)
    }

    internal data class Res(@DimenRes val resId: Int, val fallback: Number) : LazyDimen() {
        override fun resolve(context: Context): Dimen = context.dimen(resId, fallback)
    }

    internal data class Attr(@AttrRes val attrId: Int, val fallback: Number) : LazyDimen() {
        override fun resolve(context: Context): Dimen = context.dimenAttr(attrId, fallback)
    }
}
