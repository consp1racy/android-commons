@file:JvmName("XpSwipeRefreshLayout")
package net.xpece.android.view

import android.graphics.Color
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import net.xpece.android.R
import net.xpece.android.content.resolveColor

fun SwipeRefreshLayout.setupDefaultColors() {
    setColorSchemeColors(context.resolveColor(R.attr.colorAccent, Color.BLACK))
    setProgressBackgroundColorSchemeColor(
            context.resolveColor(R.attr.colorBackgroundFloating, Color.WHITE))
}
