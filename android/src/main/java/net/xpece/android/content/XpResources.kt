@file:JvmName("XpContext")
@file:JvmMultifileClass

package net.xpece.android.content

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.DimenRes
import android.support.annotation.StyleRes
import net.xpece.android.content.res.Dimen

private val TEMP_ARRAY = IntArray(1)

fun Context.resolveFloat(@AttrRes attr: Int, fallback: Float = 0F): Float {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(TEMP_ARRAY)
    try {
        return ta.getFloat(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveBoolean(@AttrRes attr: Int, fallback: Boolean = false): Boolean {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(TEMP_ARRAY)
    try {
        return ta.getBoolean(0, fallback)
    } finally {
        ta.recycle()
    }
}

@ColorInt
fun Context.resolveColor(@AttrRes attr: Int, @ColorInt fallback: Int = 0): Int {
    val resId = resolveResourceId(attr, 0)
    return getColorCompat(resId, fallback)
}

fun Context.resolveColorStateList(@AttrRes attr: Int): ColorStateList? {
    val resId = resolveResourceId(attr, 0)
    return getColorStateListCompat(resId)
}

fun Context.resolveDimension(@AttrRes attr: Int, fallback: Float = 0F): Float {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(TEMP_ARRAY)
    try {
        return ta.getDimension(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDimensionPixelOffset(@AttrRes attr: Int, fallback: Int = 0): Int {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(TEMP_ARRAY)
    try {
        return ta.getDimensionPixelOffset(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDimensionPixelSize(@AttrRes attr: Int, fallback: Int = 0): Int {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(TEMP_ARRAY)
    try {
        return ta.getDimensionPixelSize(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDrawable(@AttrRes attr: Int): Drawable? {
    val resId = resolveResourceId(attr, 0)
    return getDrawableCompat(resId)
}

fun Context.resolveString(@AttrRes attr: Int): String {
    val resId = resolveResourceId(attr, 0)
    return getString(resId)
}

fun Context.resolveText(@AttrRes attr: Int): CharSequence? {
    val resId = resolveResourceId(attr, 0)
    return getText(resId)
}

fun Context.resolveResourceId(@AttrRes attr: Int, fallback: Int): Int {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(TEMP_ARRAY)
    try {
        return ta.getResourceId(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveFloat(@StyleRes style: Int, @AttrRes attr: Int, fallback: Float): Float {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(style, TEMP_ARRAY)
    try {
        return ta.getFloat(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveBoolean(@StyleRes style: Int, @AttrRes attr: Int, fallback: Boolean): Boolean {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(style, TEMP_ARRAY)
    try {
        return ta.getBoolean(0, fallback)
    } finally {
        ta.recycle()
    }
}

@ColorInt
fun Context.resolveColor(@StyleRes style: Int, @AttrRes attr: Int, @ColorInt fallback: Int = 0): Int {
    val resId = resolveResourceId(style, attr, 0)
    return getColorCompat(resId, fallback)
}

fun Context.resolveColorStateList(@StyleRes style: Int, @AttrRes attr: Int): ColorStateList? {
    val resId = resolveResourceId(style, attr, 0)
    return getColorStateListCompat(resId)
}

fun Context.resolveDimension(@StyleRes style: Int, @AttrRes attr: Int, fallback: Float = 0F): Float {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(style, TEMP_ARRAY)
    try {
        return ta.getDimension(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDimensionPixelOffset(@StyleRes style: Int, @AttrRes attr: Int, fallback: Int = 0): Int {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(style, TEMP_ARRAY)
    try {
        return ta.getDimensionPixelOffset(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDimensionPixelSize(@StyleRes style: Int, @AttrRes attr: Int, fallback: Int = 0): Int {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(style, TEMP_ARRAY)
    try {
        return ta.getDimensionPixelSize(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDrawable(@StyleRes style: Int, @AttrRes attr: Int): Drawable? {
    val resId = resolveResourceId(style, attr, 0)
    return getDrawableCompat(resId)
}

fun Context.resolveString(@StyleRes style: Int, @AttrRes attr: Int): String? {
    val resId = resolveResourceId(style, attr, 0)
    return getString(resId)
}

fun Context.resolveText(@StyleRes style: Int, @AttrRes attr: Int): CharSequence? {
    val resId = resolveResourceId(style, attr, 0)
    return getText(resId)
}

fun Context.resolveResourceId(@StyleRes style: Int, @AttrRes attr: Int, fallback: Int): Int {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(style, TEMP_ARRAY)
    try {
        return ta.getResourceId(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun sp(sp: Int): Dimen = Dimen.sp(sp)
fun dp(dp: Int): Dimen = Dimen.dp(dp)
fun dimen(@DimenRes resId: Int): Dimen = Dimen.res(resId)
fun Context.dimenAttr(@AttrRes attrId: Int): Dimen = Dimen.attr(this, attrId)
