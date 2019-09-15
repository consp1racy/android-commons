@file:JvmName("XpResources")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.content

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import net.xpece.android.content.BaseResources.obtainStyledAttributes
import net.xpece.android.content.BaseResources.resolveResourceId as resolveResourceIdImpl

@AnyRes
inline fun Context.resolveResourceId(@AttrRes attr: Int, @AnyRes fallback: Int): Int =
        resolveResourceIdImpl(0, attr, fallback)

@AnyRes
inline fun Context.resolveResourceId(@StyleRes style: Int, @AttrRes attr: Int, @AnyRes fallback: Int): Int =
        resolveResourceIdImpl(style, attr, fallback)

/**
 * @throws NullPointerException
 */
@ColorInt
inline fun Context.getColorCompat(@ColorRes resId: Int): Int = getColorStateListCompat(resId).defaultColor

/**
 * @throws NullPointerException
 */
fun Context.getColorStateListCompat(@ColorRes resId: Int): ColorStateList =
        AppCompatResources.getColorStateList(this, resId)!!

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
    return AppCompatResources.getDrawable(this, resId)!!
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
    val ta = obtainStyledAttributes(style, attr)
    try {
        return ta.getFloat(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveBoolean(@StyleRes style: Int, @AttrRes attr: Int, fallback: Boolean): Boolean {
    val ta = obtainStyledAttributes(style, attr)
    try {
        return ta.getBoolean(0, fallback)
    } finally {
        ta.recycle()
    }
}

@ColorInt
fun Context.resolveColor(
        @StyleRes style: Int, @AttrRes attr: Int, @ColorInt fallback: Int = 0): Int {
    val ta = obtainStyledAttributes(style, attr)
    try {
        return ta.getColorCompat(this, 0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveColorStateList(@StyleRes style: Int, @AttrRes attr: Int): ColorStateList? {
    val ta = obtainStyledAttributes(style, attr)
    try {
        return ta.getColorStateListCompat(this, 0)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDimension(
        @StyleRes style: Int, @AttrRes attr: Int, fallback: Float = 0F): Float {
    val ta = obtainStyledAttributes(style, attr)
    try {
        return ta.getDimension(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDimensionPixelOffset(
        @StyleRes style: Int, @AttrRes attr: Int, fallback: Int = 0): Int {
    val ta = obtainStyledAttributes(style, attr)
    try {
        return ta.getDimensionPixelOffset(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDimensionPixelSize(
        @StyleRes style: Int, @AttrRes attr: Int, fallback: Int = 0): Int {
    val ta = obtainStyledAttributes(style, attr)
    try {
        return ta.getDimensionPixelSize(0, fallback)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveDrawable(@StyleRes style: Int, @AttrRes attr: Int): Drawable? {
    val ta = obtainStyledAttributes(style, attr)
    try {
        return ta.getDrawableCompat(this, 0)
    } finally {
        ta.recycle()
    }
}

fun Context.resolveString(@StyleRes style: Int, @AttrRes attr: Int): String? {
    val ta = obtainStyledAttributes(style, attr)
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
    val ta = obtainStyledAttributes(style, attr)
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

fun TypedArray.getDrawableCompat(context: Context, @StyleableRes index: Int): Drawable? {
    val resId = getResourceId(index, 0)
    return if (resId != 0) {
        // It's a reference, let Context handle it.
        context.getDrawableCompat(resId)
    } else {
        // It's in-place, obtain it form TypedArray. (Maybe a color int?)
        getDrawable(0)
    }
}

fun TypedArray.getColorStateListCompat(context: Context, @StyleableRes index: Int): ColorStateList? {
    val resId = getResourceId(index, 0)
    return if (resId != 0) {
        // It's a reference, let Context handle it.
        context.getColorStateListCompat(resId)
    } else {
        // It's in-place, obtain it form TypedArray. (Maybe a color int?)
        getColorStateList(0)
    }
}

@ColorInt
fun TypedArray.getColorCompat(
        context: Context,
        @StyleableRes index: Int,
        @ColorInt defValue: Int
): Int {
    val resId = getResourceId(index, 0)
    return if (resId != 0) {
        // It's a reference, let Context handle it.
        context.getColorCompat(resId)
    } else {
        // It's in-place, obtain it form TypedArray. (Maybe a color int?)
        getColor(0, defValue)
    }
}
