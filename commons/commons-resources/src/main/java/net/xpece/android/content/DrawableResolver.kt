package net.xpece.android.content

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import java.util.*


interface DrawableResolver {
    companion object Configuration {
        var isDrawableResolversEnabled = false
        val drawableResolvers: MutableList<DrawableResolver> = ArrayList()
    }

    fun getDrawable(context: Context, @DrawableRes resId: Int): Drawable?
}
