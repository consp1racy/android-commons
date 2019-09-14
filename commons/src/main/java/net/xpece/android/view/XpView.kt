@file:JvmMultifileClass
@file:JvmName("XpView")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.view

import android.annotation.TargetApi
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ScrollView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.text.TextUtilsCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import net.xpece.android.content.res.layoutDirectionCompat
import java.lang.reflect.Method
import java.util.*

private val methodViewRemovePerformClickCallback by lazy(LazyThreadSafetyMode.NONE) {
    View::class.java
            .getDeclaredMethod("removePerformClickCallback")
            .apply { isAccessible = true }
}

fun View.cancelPendingInputEventsCompat() {
    if (Build.VERSION.SDK_INT >= 19) {
        cancelPendingInputEvents()
    } else {
        methodViewRemovePerformClickCallback.invoke(this)
        cancelLongPress()
    }
}

inline fun View.gone(): View = apply {
    visibility = View.GONE
}

inline fun View.invisible(): View = apply {
    visibility = View.INVISIBLE
}

inline fun View.visible(): View = apply {
    visibility = View.VISIBLE
}

inline fun View.setBackgroundCompat(d: Drawable) {
    @Suppress("DEPRECATION")
    setBackgroundDrawable(d)
}

inline fun View.removeOnGlobalLayoutListenerCompat(l: ViewTreeObserver.OnGlobalLayoutListener) {
    @Suppress("deprecation")
    viewTreeObserver.removeGlobalOnLayoutListener(l)
}

/**
 * @return Returns true this ScrollView can be scrolled
 */
fun ScrollView.canScroll(): Boolean {
    val child = getChildAt(0)
    if (child != null) {
        val lp = child.layoutParams as ViewGroup.MarginLayoutParams
        val childSize = child.height + lp.topMargin + lp.bottomMargin
        val parentSpace = height - paddingTop - paddingBottom
        return childSize > parentSpace
    }
    return false
}

@JvmOverloads
@Deprecated("Use tooltips from the support library version 26.")
fun View.toastContentDescription(text: CharSequence = this.contentDescription): Boolean {
    val screenPos = IntArray(2)
    val displayFrame = Rect()
    getLocationOnScreen(screenPos)
    getWindowVisibleDisplayFrame(displayFrame)

    val context = context
    val width = width
    val height = height
    val midy = screenPos[1] + height / 2
    var referenceX = screenPos[0] + width / 2
    if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_LTR) {
        val screenWidth = context.resources.displayMetrics.widthPixels
        referenceX = screenWidth - referenceX // mirror
    }

    val cheatSheet = Toast.makeText(context, text, Toast.LENGTH_SHORT)
    if (midy < displayFrame.height()) {
        // Show below the tab view
        cheatSheet.setGravity(
                Gravity.TOP or GravityCompat.END, referenceX,
                screenPos[1] + height - displayFrame.top)
    } else {
        // Show along the bottom center
        cheatSheet.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, height)
    }
    cheatSheet.show()
    return true
}

@Deprecated("")
@Suppress("DEPRECATION")
fun View.fitSystemWindows(insets: Rect) = XpViewReflect.fitSystemWindows(this, insets)

inline fun View.setLayerTypeSafe(layerType: Int) {
    if (this.layerType != layerType) {
        setLayerType(layerType, null)
    }
}

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
    return when (val rawLayoutDirection = rawLayoutDirection) {
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
