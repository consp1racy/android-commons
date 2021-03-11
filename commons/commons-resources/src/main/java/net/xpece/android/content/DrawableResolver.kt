@file:Suppress("DEPRECATION")

package net.xpece.android.content

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import java.util.*

/**
 * Plugin mechanism for [getDrawableCompat].
 */
@Deprecated("Without replacement.")
interface DrawableResolver {

    companion object Configuration {

        /**
         * Whether the [DrawableResolver] plugin mechanism is enabled.
         *
         * Default: `false`
         */
        var isDrawableResolversEnabled = false

        /**
         * User-added list of [DrawableResolver]s, evaluated on FIFO basis.
         */
        val drawableResolvers: MutableList<DrawableResolver> = ArrayList()
    }

    /**
     * Resolve [Drawable] from [resId] using themed [Context].
     * @return A resolved Drawable, or `null` if another [DrawableResolver] should try.
     */
    fun getDrawable(context: Context, @DrawableRes resId: Int): Drawable?
}
