@file:JvmName("XpResources")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.content

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.appcompat.content.res.AppCompatResources
import net.xpece.android.content.BaseResources.obtainTypedArray
import net.xpece.android.content.BaseResources.resolveResourceId as resolveResourceIdImpl

@AnyRes
inline fun Context.resolveResourceId(@AttrRes attr: Int, fallback: Int): Int =
        resolveResourceIdImpl(0, attr, fallback)

@AnyRes
inline fun Context.resolveResourceId(@StyleRes style: Int, @AttrRes attr: Int, fallback: Int): Int =
        resolveResourceIdImpl(style, attr, fallback)

/**
 * @throws NullPointerException
 */
@ColorInt
inline fun Context.getColorCompat(@ColorRes resId: Int): Int
        = getColorStateListCompat(resId).defaultColor

/**
 * @throws NullPointerException
 */
fun Context.getColorStateListCompat(@ColorRes resId: Int): ColorStateList = try {
    AppCompatResources.getColorStateList(this, resId)!!
} catch (ex: NoSuchMethodError) {
    ContextCompat.getColorStateList(this, resId)!!
} catch (ex: NoClassDefFoundError) {
    ContextCompat.getColorStateList(this, resId)!!
}

/**
 * @throws NullPointerException
 */
fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable {
    if (DrawableResolver.isDrawableResolversEnabled) {
        @Suppress("LoopToCallChain")
        for (it in DrawableResolver.drawableResolvers) {
            val d = it.getDrawable(this, resId)
            if (d != null) return d
        }
    }
    return try {
        AppCompatResources.getDrawable(this, resId)!!
    } catch (ex: NoClassDefFoundError) {
        ContextCompat.getDrawable(this, resId)!!
    }
}

inline fun Context.resolveFloat(@AttrRes attr: Int, fallback: Float = 0F): Float =
        resolveFloat(0, attr, fallback)

inline fun Context.resolveBoolean(@AttrRes attr: Int, fallback: Boolean = false): Boolean =
        resolveBoolean(0, attr, fallback)

@ColorInt
inline fun Context.resolveColor(@AttrRes attr: Int, @ColorInt fallback: Int = 0): Int =
        resolveColor(0, attr, fallback)

inline fun Context.resolveColorStateList(@AttrRes attr: Int): ColorStateList? =
        resolveColorStateList(0, attr)

inline fun Context.resolveDimension(@AttrRes attr: Int, fallback: Float = 0F): Float =
        resolveDimension(0, attr, fallback)

inline fun Context.resolveDimensionPixelOffset(@AttrRes attr: Int, fallback: Int = 0): Int =
        resolveDimensionPixelOffset(0, attr, fallback)

inline fun Context.resolveDimensionPixelSize(@AttrRes attr: Int, fallback: Int = 0): Int =
        resolveDimensionPixelSize(0, attr, fallback)

inline fun Context.resolveDrawable(@AttrRes attr: Int): Drawable? =
        resolveDrawable(0, attr)

inline fun Context.resolveString(@AttrRes attr: Int): String? =
        resolveString(0, attr)

inline fun Context.resolveText(@AttrRes attr: Int): CharSequence? =
        resolveText(0, attr)

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
fun Context.resolveColor(
        @StyleRes style: Int, @AttrRes attr: Int, @ColorInt fallback: Int = 0): Int {
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

fun Context.resolveDimension(
        @StyleRes style: Int, @AttrRes attr: Int, fallback: Float = 0F): Float {
    val ta = obtainTypedArray(style, attr)
    try {
        return ta.getDimension(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDimensionPixelOffset(
        @StyleRes style: Int, @AttrRes attr: Int, fallback: Int = 0): Int {
    val ta = obtainTypedArray(style, attr)
    try {
        return ta.getDimensionPixelOffset(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDimensionPixelSize(
        @StyleRes style: Int, @AttrRes attr: Int, fallback: Int = 0): Int {
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
