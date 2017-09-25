@file:JvmName("Dimens")

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

fun init(context: Context) {
    sContext = context.applicationContext
}

fun px(px: Number): Dimen = Dimen(px.toFloat())

@Deprecated("Not suitable for multiscreen environment.")
fun sp(sp: Number): Dimen = sContext.sp(sp)

@Deprecated("Not suitable for multiscreen environment.")
fun dp(dp: Number): Dimen = sContext.dp(dp)

@Deprecated("Not suitable for multiscreen environment.")
fun dimen(@DimenRes resId: Int): Dimen = sContext.dimen(resId)

fun Context.sp(sp: Number): Dimen {
    val spf = sp.toFloat()
    val value = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, spf, resources.displayMetrics)
    return Dimen(value)
}

fun Context.dp(dp: Number): Dimen {
    val dpf = dp.toFloat()
    val value = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dpf, resources.displayMetrics)
    return Dimen(value)
}

@JvmOverloads
fun Context.dimen(@DimenRes resId: Int, fallback: Int = 0): Dimen = try {
    Dimen(resources.getDimension(resId))
} catch (erx: Resources.NotFoundException) {
    Dimen(fallback.toFloat())
}

@JvmOverloads
fun Context.dimenAttr(@AttrRes attrId: Int, fallback: Int = 0): Dimen {
    val resId = resolveResourceId(attrId, 0)
    return dimen(resId, fallback)
}
