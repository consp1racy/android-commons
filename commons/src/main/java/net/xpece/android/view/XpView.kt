@file:JvmName("XpView")

package net.xpece.android.view

import android.animation.LayoutTransition
import android.annotation.TargetApi
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.annotation.StyleRes
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v4.widget.TextViewCompat
import android.support.v7.widget.XpAppCompatResources
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.*
import net.xpece.android.R
import net.xpece.android.content.resolveColor

/**
 * @author Eugen on 29.07.2016.
 */

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

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun View.setVisible(visible: Boolean) = if (visible) visibility = View.VISIBLE else visibility = View.GONE

@JvmOverloads
fun TextView.setTextAndVisibility(text: CharSequence?, invisible: Boolean = false) {
    if (text.isNullOrBlank()) {
        if (invisible) invisible() else gone()
        setText(null)
    } else {
        setText(text)
        visible()
    }
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

fun setSearchViewLayoutTransition(view: SearchView) {
    if (Build.VERSION.SDK_INT < 11) return
    val searchBarId = view.context.resources.getIdentifier("android:id/search_bar", null, null)
    val searchBar = view.findViewById(searchBarId) as LinearLayout
    searchBar.layoutTransition = LayoutTransition()
}

fun setSearchViewLayoutTransition(view: android.support.v7.widget.SearchView) {
    if (Build.VERSION.SDK_INT < 11) return
    val searchBar = view.findViewById(R.id.search_bar) as LinearLayout
    searchBar.layoutTransition = LayoutTransition()
}

@JvmOverloads
fun ImageView.switchImage(d: Drawable?, duration: Int = 100) {
    if (ViewCompat.isLaidOut(this)) {
        var old = drawable
        if (old is TransitionDrawable) {
            old = old.getDrawable(1)
        } else if (old == null) {
            if (d == null) {
                return
            } else {
                old = ColorDrawable(Color.TRANSPARENT)
            }
        }
        val target = d ?: ColorDrawable(Color.TRANSPARENT)
        val transition = TransitionDrawable(arrayOf(old, target))
        transition.isCrossFadeEnabled = true
        setImageDrawable(transition)
        transition.startTransition(duration)
    } else {
        setImageDrawable(d)
    }
}

@JvmOverloads
fun ImageView.switchImage(@DrawableRes resId: Int, duration: Int = 100) {
    val d = XpAppCompatResources.getDrawable(context, resId)
    switchImage(d, duration)
}

@JvmOverloads
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
        cheatSheet.setGravity(Gravity.TOP or GravityCompat.END, referenceX,
                screenPos[1] + height - displayFrame.top)
    } else {
        // Show along the bottom center
        cheatSheet.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, height)
    }
    cheatSheet.show()
    return true
}

val View.isRtl: Boolean
    get() = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL

fun View.fitSystemWindows(insets: Rect) = XpViewReflect.fitSystemWindows(this, insets)

fun SwipeRefreshLayout.setupDefaultColors() {
    setColorSchemeColors(context.resolveColor(R.attr.colorAccent, Color.BLACK))
    setProgressBackgroundColorSchemeColor(context.resolveColor(R.attr.colorBackgroundFloating, Color.WHITE))
}

fun TextView.setTextAppearanceCompat(@StyleRes resId: Int) = TextViewCompat.setTextAppearance(this, resId)
