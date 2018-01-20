@file:JvmName("Dimens")
@file:JvmMultifileClass

package net.xpece.android.content

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.support.annotation.AttrRes
import android.support.annotation.DimenRes
import android.util.TypedValue
import net.xpece.android.content.res.Dimen

@SuppressLint("StaticFieldLeak")
private var sContext: Context = object : ContextWrapper(null) {
    override fun getResources(): Resources {
        throw IllegalStateException(
                "You forgot to call ${this::class.java.simpleName}.init(Context).")
    }
}

@Deprecated("Do not use dimensions without an intermediate context.")
object DimensLegacyInitializer {
    @JvmStatic
    fun init(context: Context) {
        sContext = context.applicationContext
    }
}

fun px(px: Number): Dimen = Dimen(px.toFloat())

@Deprecated("Not suitable for multiscreen environment.")
fun sp(sp: Number): Dimen = sContext.sp(sp)

@Deprecated("Not suitable for multiscreen environment.")
fun dp(dp: Number): Dimen = sContext.dp(dp)

@Deprecated("Not suitable for multiscreen environment.")
fun dimen(@DimenRes resId: Int): Dimen = sContext.dimen(resId)

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

fun Context.sp(sp: Number): Dimen = resources.sp(sp)

fun Context.dp(dp: Number): Dimen = resources.dp(dp)

@JvmOverloads
fun Context.dimen(@DimenRes resId: Int, fallback: Number = 0): Dimen =
        resources.dimen(resId, fallback)

@JvmOverloads
fun Context.dimenAttr(@AttrRes attrId: Int, fallback: Number = 0): Dimen {
    val resId = resolveResourceId(attrId, 0)
    return dimen(resId, fallback)
}
