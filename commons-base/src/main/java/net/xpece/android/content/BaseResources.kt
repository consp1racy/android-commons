package net.xpece.android.content

import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.AttrRes
import android.support.annotation.StyleRes

private val TEMP_ARRAY = ThreadLocal<IntArray>()

private fun getTempArray(): IntArray {
    var tempArray = TEMP_ARRAY.get()
    if (tempArray == null) {
        tempArray = IntArray(1)
        TEMP_ARRAY.set(tempArray)
    }
    return tempArray
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

fun Context.obtainTypedArray(style: Int, attr: Int): TypedArray {
    val tempArray = getTempArray()
    tempArray[0] = attr
    return obtainStyledAttributes(style, tempArray)
}
