@file:JvmName("XpContext")
@file:JvmMultifileClass

package net.xpece.android.content

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.XpAppCompatResources
import net.xpece.android.content.res.Dimen

private val TEMP_ARRAY = ThreadLocal<IntArray>()

private fun getTempArray(): IntArray {
    var tempArray = TEMP_ARRAY.get()
    if (tempArray == null) {
        tempArray = IntArray(1)
        TEMP_ARRAY.set(tempArray)
    }
    return tempArray
}

@ColorInt
fun Context.getColorCompat(@ColorRes resId: Int): Int
        = getColorStateListCompat(resId).defaultColor

fun Context.getColorStateListCompat(@ColorRes resId: Int): ColorStateList {
    try {
        return AppCompatResources.getColorStateList(this, resId)
    } catch (ex: NoSuchMethodError) {
        return ContextCompat.getColorStateList(this, resId)
    }
}

fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable? {
    if (DrawableResolver.isDrawableResolversEnabled) {
        DrawableResolver.drawableResolvers.forEach {
            val d = it.getDrawable(this, resId)
            if (d != null) return d
        }
    }
    return XpAppCompatResources.getDrawable(this, resId)
}

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
    val ta = obtainTypedArray(style, attr)
    try {
        return ta.getFloat(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveBoolean(@StyleRes style: Int, @AttrRes attr: Int, fallback: Boolean): Boolean {
    val ta = obtainTypedArray(style, attr)
    try {
        return ta.getBoolean(0, fallback)
    } finally {
        ta.recycle()
    }
}

@ColorInt
fun Context.resolveColor(@StyleRes style: Int, @AttrRes attr: Int, @ColorInt fallback: Int = 0): Int {
    val ta = obtainTypedArray(style, attr)
    try {
        val resId = ta.getResourceId(0, 0)
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
    val ta = obtainTypedArray(style, attr)
    try {
        val resId = ta.getResourceId(0, 0)
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
    val ta = obtainTypedArray(style, attr)
    try {
        return ta.getDimension(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDimensionPixelOffset(@StyleRes style: Int, @AttrRes attr: Int, fallback: Int = 0): Int {
    val ta = obtainTypedArray(style, attr)
    try {
        return ta.getDimensionPixelOffset(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDimensionPixelSize(@StyleRes style: Int, @AttrRes attr: Int, fallback: Int = 0): Int {
    val ta = obtainTypedArray(style, attr)
    try {
        return ta.getDimensionPixelSize(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDrawable(@StyleRes style: Int, @AttrRes attr: Int): Drawable? {
    val ta = obtainTypedArray(style, attr)
    try {
        val resId = ta.getResourceId(0, 0)
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
    val ta = obtainTypedArray(style, attr)
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
    val ta = obtainTypedArray(style, attr)
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
    val ta = obtainTypedArray(style, attr)
    try {
        return ta.getResourceId(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun px(px: Number): Dimen = Dimen.px(px)
fun sp(sp: Number): Dimen = Dimen.sp(sp)
fun dp(dp: Number): Dimen = Dimen.dp(dp)
fun dimen(@DimenRes resId: Int): Dimen = Dimen.res(resId)
fun Context.dimenAttr(@AttrRes attrId: Int): Dimen = Dimen.attr(this, attrId)

private fun Context.obtainTypedArray(style: Int, attr: Int): TypedArray {
    val tempArray = getTempArray()
    tempArray[0] = attr
    val ta = obtainStyledAttributes(style, tempArray)
    return ta
}
