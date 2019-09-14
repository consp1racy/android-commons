@file:JvmName("Dimens")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.content

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import net.xpece.android.content.BaseResources.resolveResourceId
import net.xpece.android.content.res.Dimen

fun px(px: Number): Dimen = Dimen(px.toFloat())

fun Resources.sp(sp: Number): Dimen {
    val spf = sp.toFloat()
    val value = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spf, displayMetrics)
    return Dimen(value)
}

fun Resources.dp(dp: Number): Dimen {
    val dpf = dp.toFloat()
    val value = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dpf, displayMetrics)
    return Dimen(value)
}

@JvmOverloads
fun Resources.dimen(@DimenRes resId: Int, fallback: Number = 0): Dimen = try {
    Dimen(getDimension(resId))
} catch (erx: Resources.NotFoundException) {
    Dimen(fallback.toFloat())
}

inline fun Context.sp(sp: Number): Dimen =
        resources.sp(sp)

inline fun Context.dp(dp: Number): Dimen =
        resources.dp(dp)

@JvmOverloads
inline fun Context.dimen(@DimenRes resId: Int, fallback: Number = 0): Dimen =
        resources.dimen(resId, fallback)

@JvmOverloads
fun Context.dimenAttr(@AttrRes attrId: Int, fallback: Number = 0): Dimen {
    val resId = resolveResourceId(attrId, 0)
    return dimen(resId, fallback)
}
