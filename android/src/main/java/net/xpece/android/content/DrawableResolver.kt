package net.xpece.android.content

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes

/**
 * @author Eugen on 28.07.2016.
 */

interface DrawableResolver {
    companion object {
        var isDrawableResolversEnabled = false
        val drawableResolvers = mutableListOf<DrawableResolver>()
    }
    fun getDrawable(context: Context, @DrawableRes resId: Int): Drawable?
}
