package net.xpece.android.view

import android.graphics.Outline
import android.support.annotation.RequiresApi
import android.view.View
import android.view.ViewOutlineProvider

/**
 * Created by Eugen on 26.11.2016.
 */

object XpViewOutlineProvider {
    @JvmStatic
    @RequiresApi(21)
    val NONE: ViewOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRect(0, 0, view.width, view.height)
            outline.alpha = 0.0f
        }
    }
}
