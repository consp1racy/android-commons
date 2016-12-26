package net.xpece.android.view

import android.annotation.TargetApi
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider

/**
 * Created by Eugen on 26.11.2016.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
object XpViewOutlineProvider {
    @JvmStatic
    val NONE: ViewOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRect(0, 0, view.width, view.height)
            outline.alpha = 0.0f
        }
    }
}
