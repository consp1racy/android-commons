@file:JvmName("XpView")

package net.xpece.android.view

import android.animation.LayoutTransition
import android.annotation.TargetApi
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.v4.view.ViewCompat
import android.support.v7.widget.AppCompatDrawableManager
import android.view.View
import android.view.ViewTreeObserver
import android.widget.*
import net.xpece.android.R

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
@Suppress("deprecation")
fun View.setBackgroundCompat(d: Drawable) {
    if (Build.VERSION.SDK_INT < 16) {
        setBackgroundDrawable(d)
    } else {
        background = d
    }
}

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@Suppress("deprecation")
fun View.removeOnGlobalLayoutListenerCompat(l: ViewTreeObserver.OnGlobalLayoutListener) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
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

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
fun setSearchViewLayoutTransition(view: SearchView) {
    if (Build.VERSION.SDK_INT < 11) return
    val searchBarId = view.context.resources.getIdentifier("android:id/search_bar", null, null)
    val searchBar = view.findViewById(searchBarId) as LinearLayout
    searchBar.layoutTransition = LayoutTransition()
}

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
fun setSearchViewLayoutTransition(view: android.support.v7.widget.SearchView) {
    if (Build.VERSION.SDK_INT < 11) return
    val searchBar = view.findViewById(R.id.search_bar) as LinearLayout
    searchBar.layoutTransition = LayoutTransition()
}

fun ImageView.switchImage(d: Drawable, duration: Int = 100) {
    if (ViewCompat.isLaidOut(this)) {
        var old = drawable
        if (old is TransitionDrawable) {
            old = old.getDrawable(1)
        } else if (old == null) {
            old = ColorDrawable(0)
        }
        val d2 = TransitionDrawable(arrayOf(old, d))
        d2.isCrossFadeEnabled = true
        setImageDrawable(d2)
        d2.startTransition(duration)
    } else {
        setImageDrawable(d)
    }
}

fun ImageView.switchImage(@DrawableRes resId: Int, duration: Int = 100) {
    val d = AppCompatDrawableManager.get().getDrawable(context, resId)
    switchImage(d, duration)
}
