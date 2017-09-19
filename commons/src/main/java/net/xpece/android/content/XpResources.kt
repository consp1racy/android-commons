@file:JvmName("XpContext")
@file:JvmMultifileClass

package net.xpece.android.content

import android.content.Context
import android.graphics.Point
import net.xpece.android.app.windowManager

private val TL_POINT = object : ThreadLocal<Point>() {
    override fun initialValue() = Point()
}

val Context.windowWidth: Int
    get() {
        val point = TL_POINT.get()
        windowManager.defaultDisplay.getSize(point)
        return point.x
    }

val Context.smallestWindowWidth: Int
    get() {
        val point = TL_POINT.get()
        windowManager.defaultDisplay.getSize(point)
        return Math.min(point.x, point.y)
    }
