@file:JvmName("XpContext")
@file:JvmMultifileClass

package net.xpece.android.content

import android.content.Context
import android.graphics.Point
import net.xpece.android.app.windowManager

private val TL_POINT = object : ThreadLocal<Point>() {
    override fun initialValue() = Point()
}

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
private val point: Point
    get() = TL_POINT.get()

val Context.windowWidth: Int
    get() = point.apply(windowManager.defaultDisplay::getSize).x

val Context.smallestWindowWidth: Int
    get() = point.apply(windowManager.defaultDisplay::getSize).let { minOf(it.x, it.y) }
