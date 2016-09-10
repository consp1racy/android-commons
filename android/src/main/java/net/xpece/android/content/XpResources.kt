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

fun Context.resolveFloat(@AttrRes attr: Int, fallback: Float = 0F) = resolveFloat(0, attr, fallback)

fun Context.resolveBoolean(@AttrRes attr: Int, fallback: Boolean = false) = resolveBoolean(0, attr, fallback)

@ColorInt
fun Context.resolveColor(@AttrRes attr: Int, @ColorInt fallback: Int = 0) = resolveColor(0, attr, fallback)

fun Context.resolveColorStateList(@AttrRes attr: Int) = resolveColorStateList(0, attr)

fun Context.resolveDimension(@AttrRes attr: Int, fallback: Float = 0F) = resolveDimension(0, attr, fallback)

fun Context.resolveDimensionPixelOffset(@AttrRes attr: Int, fallback: Int = 0) = resolveDimensionPixelOffset(0, attr, fallback)

fun Context.resolveDimensionPixelSize(@AttrRes attr: Int, fallback: Int = 0) = resolveDimensionPixelSize(0, attr, fallback)

fun Context.resolveDrawable(@AttrRes attr: Int) = resolveDrawable(0, attr)

fun Context.resolveString(@AttrRes attr: Int) = resolveString(0, attr)

fun Context.resolveText(@AttrRes attr: Int) = resolveText(0, attr)

fun Context.resolveResourceId(@AttrRes attr: Int, fallback: Int) = resolveResourceId(0, attr, fallback)

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
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(style, TEMP_ARRAY)
    try {
        val resId = resolveResourceId(style, attr, 0)
        if (resId != 0) {
            // It's a reference, let Context handle it.
            return getColorCompat(resId)
        } else {
            // It's in-place, obtain it form TypedArray. (Maybe a color int?)
            return ta.getColor(0, fallback)
        }
    } finally {
        ta.recycle()
    }
}

fun Context.resolveColorStateList(@StyleRes style: Int, @AttrRes attr: Int): ColorStateList? {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(style, TEMP_ARRAY)
    try {
        val resId = resolveResourceId(style, attr, 0)
        if (resId != 0) {
            // It's a reference, let Context handle it.
            return getColorStateListCompat(resId)
        } else {
            // It's in-place, obtain it form TypedArray. (Maybe a color int?)
            return ta.getColorStateList(0)
        }
    } finally {
        ta.recycle()
    }
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
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(style, TEMP_ARRAY)
    try {
        val resId = resolveResourceId(style, attr, 0)
        if (resId != 0) {
            // It's a reference, let Context handle it.
            return getDrawableCompat(resId)
        } else {
            // It's in-place, obtain it form TypedArray. (Maybe a color int?)
            return ta.getDrawable(0)
        }
    } finally {
        ta.recycle()
    }
}

fun Context.resolveString(@StyleRes style: Int, @AttrRes attr: Int): String? {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(style, TEMP_ARRAY)
    try {
        val resId = ta.getResourceId(0, 0)
        if (resId != 0) {
            // It's a reference, let Context handle it.
            return getString(resId)
        } else {
            // It's in-place, obtain it form TypedArray.
            return ta.getString(0)
        }
    } finally {
        ta.recycle()
    }
}

fun Context.resolveText(@StyleRes style: Int, @AttrRes attr: Int): CharSequence? {
    TEMP_ARRAY[0] = attr
    val ta = obtainStyledAttributes(style, TEMP_ARRAY)
    try {
        val resId = ta.getResourceId(0, 0)
        if (resId != 0) {
            // It's a reference, let Context handle it.
            return getText(resId)
        } else {
            // It's in-place, obtain it form TypedArray.
            return ta.getText(0)
        }
    } finally {
        ta.recycle()
    }
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
