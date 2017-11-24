@file:JvmName("LazyDimens")
@file:JvmMultifileClass

package net.xpece.android.content

import android.support.annotation.AttrRes
import android.support.annotation.DimenRes
import net.xpece.android.content.res.LazyDimen

fun lazyDp(dp: Number): LazyDimen = LazyDimen.Dp(dp)

fun lazySp(sp: Number): LazyDimen = LazyDimen.Sp(sp)

fun lazyDimen(@DimenRes resId: Int, fallback: Number = 0): LazyDimen =
        LazyDimen.Res(resId, fallback)

fun lazyDimenAttr(@AttrRes attrId: Int, fallback: Number = 0): LazyDimen =
        LazyDimen.Attr(attrId, fallback)
