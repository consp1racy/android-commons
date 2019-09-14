package net.xpece.android.graphics.drawable

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.TintAwareDrawable
import net.xpece.android.content.getDrawableCompat

/**
 * Drawable which loads lazily.
 */
open class LazyDrawable(context: Context, @DrawableRes resId: Int) : Drawable(), Drawable.Callback {

    val wrappedDrawable: Drawable by lazy(LazyThreadSafetyMode.NONE) {
        context.getDrawableCompat(resId)
    }

    override fun draw(canvas: Canvas) {
        wrappedDrawable.draw(canvas)
    }

    override fun onBoundsChange(bounds: Rect) {
        wrappedDrawable.bounds = bounds
    }

    override fun setChangingConfigurations(configs: Int) {
        wrappedDrawable.changingConfigurations = configs
    }

    override fun getChangingConfigurations(): Int {
        return wrappedDrawable.changingConfigurations
    }

    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun setDither(dither: Boolean) {
        wrappedDrawable.setDither(dither)
    }

    override fun setFilterBitmap(filter: Boolean) {
        wrappedDrawable.isFilterBitmap = filter
    }

    override fun setAlpha(alpha: Int) {
        wrappedDrawable.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        wrappedDrawable.colorFilter = cf
    }

    override fun isStateful(): Boolean {
        return wrappedDrawable.isStateful
    }

    override fun setState(stateSet: IntArray): Boolean {
        return wrappedDrawable.setState(stateSet)
    }

    override fun getState(): IntArray {
        return wrappedDrawable.state
    }

    override fun jumpToCurrentState() {
        wrappedDrawable.jumpToCurrentState()
    }

    override fun getCurrent(): Drawable {
        return wrappedDrawable.current
    }

    override fun setVisible(visible: Boolean, restart: Boolean): Boolean {
        return super.setVisible(visible, restart) || wrappedDrawable.setVisible(visible, restart)
    }

    override fun getOpacity(): Int {
        @Suppress("DEPRECATION")
        return wrappedDrawable.opacity
    }

    override fun getTransparentRegion(): Region? {
        return wrappedDrawable.transparentRegion
    }

    override fun getIntrinsicWidth(): Int {
        return wrappedDrawable.intrinsicWidth
    }

    override fun getIntrinsicHeight(): Int {
        return wrappedDrawable.intrinsicHeight
    }

    override fun getMinimumWidth(): Int {
        return wrappedDrawable.minimumWidth
    }

    override fun getMinimumHeight(): Int {
        return wrappedDrawable.minimumHeight
    }

    override fun getPadding(padding: Rect): Boolean {
        return wrappedDrawable.getPadding(padding)
    }

    /**
     * {@inheritDoc}
     */
    override fun invalidateDrawable(who: Drawable) {
        invalidateSelf()
    }

    /**
     * {@inheritDoc}
     */
    override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
        scheduleSelf(what, `when`)
    }

    /**
     * {@inheritDoc}
     */
    override fun unscheduleDrawable(who: Drawable, what: Runnable) {
        unscheduleSelf(what)
    }

    override fun onLevelChange(level: Int): Boolean {
        return wrappedDrawable.setLevel(level)
    }

    override fun setAutoMirrored(mirrored: Boolean) {
        DrawableCompat.setAutoMirrored(wrappedDrawable, mirrored)
    }

    override fun isAutoMirrored(): Boolean {
        return DrawableCompat.isAutoMirrored(wrappedDrawable)
    }

    override fun setTint(tint: Int) {
        DrawableCompat.setTint(wrappedDrawable, tint)
    }

    override fun setTintList(tint: ColorStateList?) {
        DrawableCompat.setTintList(wrappedDrawable, tint)
    }

    override fun setTintMode(tintMode: PorterDuff.Mode?) {
        if (tintMode != null) {
            DrawableCompat.setTintMode(wrappedDrawable, tintMode)
        } else {
            // AndroidX doesn't support null argument. Re-implement here.
            if (Build.VERSION.SDK_INT >= 21) {
                wrappedDrawable.setTintMode(tintMode)
            } else if (wrappedDrawable is TintAwareDrawable) {
                (wrappedDrawable as TintAwareDrawable).setTintMode(tintMode)
            }
        }
    }

    override fun setHotspot(x: Float, y: Float) {
        DrawableCompat.setHotspot(wrappedDrawable, x, y)
    }

    override fun setHotspotBounds(left: Int, top: Int, right: Int, bottom: Int) {
        DrawableCompat.setHotspotBounds(wrappedDrawable, left, top, right, bottom)
    }
}
