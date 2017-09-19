package net.xpece.android.content

import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.DimenRes
import net.xpece.android.content.res.Dimen

fun px(px: Number): Dimen = Dimen.px(px)

@Deprecated("Not suitable for multiscreen environment.")
fun sp(sp: Number): Dimen = Dimen.sp(sp)

@Deprecated("Not suitable for multiscreen environment.")
fun dp(dp: Number): Dimen = Dimen.dp(dp)

@Deprecated("Not suitable for multiscreen environment.")
fun dimen(@DimenRes resId: Int): Dimen = Dimen.res(resId)

fun Context.sp(sp: Number): Dimen = Dimen.sp(this, sp)
fun Context.dp(dp: Number): Dimen = Dimen.dp(this, dp)
fun Context.dimen(@DimenRes resId: Int): Dimen = Dimen.res(this, resId)
fun Context.dimenAttr(@AttrRes attrId: Int): Dimen = Dimen.attr(this, attrId)
