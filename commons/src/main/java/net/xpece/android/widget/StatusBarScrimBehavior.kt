package net.xpece.android.widget

import android.content.Context
import android.os.Build
import androidx.annotation.Keep
import com.google.android.material.appbar.AppBarLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.AttributeSet
import android.view.View

/**
 * @author Eugen on 10.09.2016.
 */

class StatusBarScrimBehavior : androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior<View> {
    constructor() : super()

    @Keep
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onApplyWindowInsets(coordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout, child: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        val top = insets.systemWindowInsetTop
        val lp = child.layoutParams
        lp.height = top
        return insets
    }

    override fun onLayoutChild(parent: androidx.coordinatorlayout.widget.CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        if (Build.VERSION.SDK_INT < 19) child.visibility = View.GONE
        return false
    }

    override fun layoutDependsOn(parent: androidx.coordinatorlayout.widget.CoordinatorLayout, child: View, dependency: View): Boolean {
        return Build.VERSION.SDK_INT >= 19 && dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: androidx.coordinatorlayout.widget.CoordinatorLayout, child: View, dependency: View): Boolean {
        val lp = child.layoutParams
        lp.width = dependency.width
        ViewCompat.setElevation(child, ViewCompat.getElevation(dependency))
        child.visibility = dependency.visibility
        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onDependentViewRemoved(parent: androidx.coordinatorlayout.widget.CoordinatorLayout, child: View, dependency: View) {
        child.visibility = View.GONE
    }
}
