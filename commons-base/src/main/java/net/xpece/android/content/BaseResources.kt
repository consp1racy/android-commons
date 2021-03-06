@file:JvmName("XpBaseResources")

package net.xpece.android.content

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.AttrRes
import android.support.annotation.StyleRes
import android.support.v7.widget.TintTypedArray

private val TEMP_ARRAY = object : ThreadLocal<IntArray>() {
    override fun initialValue(): IntArray = intArrayOf(0)
}

fun Context.resolveResourceId(@AttrRes attr: Int, fallback: Int): Int =
        resolveResourceId(0, attr, fallback)

fun Context.resolveResourceId(@StyleRes style: Int, @AttrRes attr: Int, fallback: Int): Int {
    val ta = obtainTypedArray(style, attr)
    try {
        return ta.getResourceId(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.obtainTypedArray(@StyleRes style: Int, @AttrRes attr: Int): TypedArray {
    val tempArray = TEMP_ARRAY.get()
    tempArray[0] = attr
    return obtainStyledAttributes(style, tempArray)
}

@SuppressLint("RestrictedApi")
fun Context.obtainTintTypedArray(@StyleRes style: Int, @AttrRes attr: Int): TintTypedArray {
    val tempArray = TEMP_ARRAY.get()
    tempArray[0] = attr
    return TintTypedArray.obtainStyledAttributes(this, style, tempArray)
}
