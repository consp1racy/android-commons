package net.xpece.android.content

import android.content.Context
import android.content.res.TypedArray
import androidx.annotation.AnyRes
import androidx.annotation.AttrRes
import androidx.annotation.RestrictTo
import androidx.annotation.StyleRes

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
object BaseResources {

    private val TEMP_ARRAY = object : ThreadLocal<IntArray>() {
        override fun initialValue(): IntArray = intArrayOf(0)
    }

    /**
     * Retrieve the resource identifier from a styled attribute in this Context's theme.
     *
     * Example:
     *
     *     val resId = context.resolveResourceId(
     *         android.R.attr.textColorPrimary,
     *         R.color.defaultColor
     *     )
     *     val color = context.getColorCompat(resId)
     *
     * @param attr The desired attribute in the style.
     * @param fallback Identifier to return if the attribute is not defined or not a resource.
     *
     * @see android.content.Context.obtainStyledAttributes
     * @see android.content.res.TypedArray.getResourceId
     */
    @Suppress("NOTHING_TO_INLINE")
    @AnyRes
    inline fun Context.resolveResourceId(@AttrRes attr: Int, @AnyRes fallback: Int): Int =
        resolveResourceId(0, attr, fallback)

    /**
     * Retrieve the resource identifier from a styled attribute in this Context's theme.
     *
     * Example:
     *
     *     val resId = context.resolveResourceId(
     *         R.style.SomeTextAppearance,
     *         android.R.attr.textColorPrimary,
     *         R.color.defaultColor
     *     )
     *     val color = context.getColorCompat(resId)
     *
     * @param style The desired style resource.
     * @param attr The desired attribute in the style.
     * @param fallback Identifier to return if the attribute is not defined or not a resource.
     *
     * @see android.content.Context.obtainStyledAttributes
     * @see android.content.res.TypedArray.getResourceId
     */
    @AnyRes
    fun Context.resolveResourceId(
        @StyleRes style: Int,
        @AttrRes attr: Int,
        @AnyRes fallback: Int
    ): Int {
        val ta = obtainStyledAttributes(style, attr)
        try {
            return ta.getResourceId(0, fallback)
        } finally {
            ta.recycle()
        }
    }

    /**
     * Retrieve a styled attribute in this Context's theme.
     *
     * Example:
     *
     *     val color = run {
     *         val t = context.obtainStyledAttributes(
     *             R.style.SomeTextAppearance,
     *             android.R.attr.textColorPrimary
     *         )
     *         try {
     *             t.getColor(0, Color.BLACK)
     *         } finally {
     *             t.recycle()
     *         }
     *     }
     *
     *
     * @param style The desired style resource.
     * @param attr The desired attribute in the style.
     * @return A single styled attribute in a [TypedArray].
     *
     * @see android.content.res.Resources.Theme.obtainStyledAttributes
     */
    fun Context.obtainStyledAttributes(@StyleRes style: Int, @AttrRes attr: Int): TypedArray {
        val tempArray = TEMP_ARRAY.get()!!
        tempArray[0] = attr
        return obtainStyledAttributes(style, tempArray)
    }
}
