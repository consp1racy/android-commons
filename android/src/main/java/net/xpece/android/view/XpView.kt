@file:JvmName("XpView")

package net.xpece.android.view

import android.animation.LayoutTransition
import android.annotation.TargetApi
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.SearchView
import android.widget.TextView
import net.xpece.android.AndroidUtils
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

fun TextView.setTextAndVisibility(text: CharSequence?) {
    if (text.isNullOrBlank()) {
        gone()
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
    if (!AndroidUtils.API_11) return
    val searchBarId = view.context.resources.getIdentifier("android:id/search_bar", null, null)
    val searchBar = view.findViewById(searchBarId) as LinearLayout
    searchBar.layoutTransition = LayoutTransition()
}

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
fun setSearchViewLayoutTransition(view: android.support.v7.widget.SearchView) {
    if (!AndroidUtils.API_11) return
    val searchBar = view.findViewById(R.id.search_bar) as LinearLayout
    searchBar.layoutTransition = LayoutTransition()
}
