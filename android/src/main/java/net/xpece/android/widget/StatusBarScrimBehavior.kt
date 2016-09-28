package net.xpece.android.widget

import android.content.Context
import android.os.Build
import android.support.annotation.Keep
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.view.WindowInsetsCompat
import android.util.AttributeSet
import android.view.View

/**
 * @author Eugen on 10.09.2016.
 */

class StatusBarScrimBehavior : CoordinatorLayout.Behavior<View> {
    constructor() : super() {
    }

    @Keep
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    override fun onApplyWindowInsets(coordinatorLayout: CoordinatorLayout, child: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        val top = insets.systemWindowInsetTop
        val lp = child.layoutParams
        lp.height = top
        return insets
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        if (Build.VERSION.SDK_INT < 19) child.visibility = View.GONE
        return false
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return Build.VERSION.SDK_INT >= 19 && dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        val lp = child.layoutParams
        lp.width = dependency.width
        ViewCompat.setElevation(child, ViewCompat.getElevation(dependency))
        child.visibility = dependency.visibility
        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View, dependency: View) {
        child.visibility = View.GONE
    }
}
