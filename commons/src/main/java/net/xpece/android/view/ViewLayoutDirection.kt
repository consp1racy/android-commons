@file:JvmMultifileClass
@file:JvmName("XpView")

package net.xpece.android.view

import android.annotation.TargetApi
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.text.TextUtilsCompat
import android.support.v4.view.ViewCompat
import android.view.View
import net.xpece.android.content.res.layoutDirectionCompat
import java.lang.reflect.Method
import java.util.*

@get:RequiresApi(17)
private val getRawLayoutDirectionMethod: Method by lazy(LazyThreadSafetyMode.NONE) {
    // This method didn't exist until API 17. It's hidden API.
    View::class.java.getDeclaredMethod("getRawLayoutDirection")
}

val View.rawLayoutDirection: Int
    @TargetApi(17) get() = when {
        Build.VERSION.SDK_INT >= 17 -> {
            getRawLayoutDirectionMethod.invoke(this) as Int // Use hidden API.
        }
        Build.VERSION.SDK_INT >= 14 -> {
            layoutDirection // Until API 17 this method was hidden and returned raw value.
        }
        else -> ViewCompat.LAYOUT_DIRECTION_LTR // Until API 14 only LTR was a thing.
    }

private fun View.resolveLayoutDirection(): Int {
    val rawLayoutDirection = rawLayoutDirection
    return when (rawLayoutDirection) {
        ViewCompat.LAYOUT_DIRECTION_LTR,
        ViewCompat.LAYOUT_DIRECTION_RTL -> {
            // If it's set to absolute value, return the absolute value.
            rawLayoutDirection
        }
        ViewCompat.LAYOUT_DIRECTION_LOCALE -> {
            // This mimics the behavior of View class.
            TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault())
        }
        ViewCompat.LAYOUT_DIRECTION_INHERIT -> {
            // This mimics the behavior of View and ViewRootImpl classes.
            // Traverse parent views until we find an absolute value or _LOCALE.
            (parent as? View)?.resolveLayoutDirection() ?: run {
                // If we're not attached return the value from Configuration object.
                resources.configuration.layoutDirectionCompat
            }
        }
        else -> throw IllegalStateException()
    }
}

val View.realLayoutDirection: Int
    @TargetApi(19) get() =
        if (Build.VERSION.SDK_INT >= 19 && isLayoutDirectionResolved) {
            layoutDirection
        } else {
            resolveLayoutDirection()
        }
