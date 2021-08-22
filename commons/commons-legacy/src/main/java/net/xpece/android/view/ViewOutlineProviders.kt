package net.xpece.android.view

import android.graphics.Outline
import androidx.annotation.RequiresApi
import android.view.View
import android.view.ViewOutlineProvider

@RequiresApi(21)
object ViewOutlineProviders {
    @JvmField
    val NONE: ViewOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRect(0, 0, view.width, view.height)
            outline.alpha = 0.0f
        }
    }
}
