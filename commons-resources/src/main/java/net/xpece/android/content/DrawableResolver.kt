package net.xpece.android.content

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import java.util.*

/**
 * @author Eugen on 28.07.2016.
 */

interface DrawableResolver {
    companion object Configuration {
        var isDrawableResolversEnabled = false
        val drawableResolvers: MutableList<DrawableResolver> = ArrayList()
    }

    fun getDrawable(context: Context, @DrawableRes resId: Int): Drawable?
}
