@file:JvmMultifileClass
@file:JvmName("XpView")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.view

import android.animation.LayoutTransition
import android.annotation.TargetApi
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewCompat
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.SearchView
import android.widget.Toast
import net.xpece.android.R

private val methodViewRemovePerformClickCallback by lazy(LazyThreadSafetyMode.NONE) {
    View::class.java.getDeclaredMethod("removePerformClickCallback").apply {
        isAccessible = true
    }
}

fun View.cancelPendingInputEventsCompat() {
    if (Build.VERSION.SDK_INT >= 19) {
        cancelPendingInputEvents()
    } else {
        methodViewRemovePerformClickCallback.invoke(this)
        cancelLongPress()
    }
}

fun View.gone(): View {
    visibility = View.GONE
    return this
}

fun View.invisible(): View {
    visibility = View.INVISIBLE
    return this
}

fun View.visible(): View {
    visibility = View.VISIBLE
    return this
}

@Deprecated(
    "Use AndroidX.",
    ReplaceWith("isVisible", imports = ["androidx.core.view.isVisible"]),
    DeprecationLevel.ERROR
)
@JvmName("isVisibleLegacy")
inline fun View.isVisible() = isVisible

@Deprecated(
    "Use AndroidX.",
    level = DeprecationLevel.ERROR
)
@JvmName("setVisibleLegacy")
@Suppress("DeprecatedCallableAddReplaceWith")
inline fun View.setVisible(visible: Boolean) {
    this.isVisible = visible
}

@Deprecated(
    "Use AndroidX.",
    ReplaceWith("isVisible", imports = ["androidx.core.view.isVisible"])
)
inline var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

@Deprecated(
    "Use AndroidX.",
    ReplaceWith("isVisible", imports = ["androidx.core.view.isVisible"]),
    DeprecationLevel.ERROR
)
inline var View.visible: Boolean
    @JvmName("isVisibleRedundant")
    @JvmSynthetic
    get() = isVisible
    @JvmName("setVisibleRedundant")
    @JvmSynthetic
    set(value) {
        isVisible = value
    }

@TargetApi(16)
fun View.setBackgroundCompat(d: Drawable) {
    if (Build.VERSION.SDK_INT < 16) {
        @Suppress("DEPRECATION")
        setBackgroundDrawable(d)
    } else {
        background = d
    }
}

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
fun View.removeOnGlobalLayoutListenerCompat(l: ViewTreeObserver.OnGlobalLayoutListener) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
        @Suppress("deprecation")
        viewTreeObserver.removeGlobalOnLayoutListener(l)
    } else {
        viewTreeObserver.removeOnGlobalLayoutListener(l)
    }
}

/**
 * @return Returns true this ScrollView can be scrolled
 */
fun ScrollView.canScroll(): Boolean {
    val child = getChildAt(0)
    if (child != null) {
        val childHeight = child.height
        return height < childHeight + paddingTop + paddingBottom
    }
    return false
}

@Deprecated("")
fun setSearchViewLayoutTransition(view: SearchView) {
    val searchBarId = view.context.resources.getIdentifier("android:id/search_bar", null, null)
    val searchBar = view.findViewById<LinearLayout>(searchBarId)
    searchBar.layoutTransition = LayoutTransition()
}

@Deprecated("")
fun setSearchViewLayoutTransition(view: android.support.v7.widget.SearchView) {
    val searchBar = view.findViewById<LinearLayout>(R.id.search_bar)
    searchBar.layoutTransition = LayoutTransition()
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

fun View.fitSystemWindows(insets: Rect) = XpViewReflect.fitSystemWindows(this, insets)

inline fun View.setLayerTypeSafe(layerType: Int) {
    if (this.layerType != layerType) {
        setLayerType(layerType, null)
    }
}
