package net.xpece.android.graphics.drawable

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.res.ColorStateList
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.RippleDrawable.RADIUS_AUTO
import android.os.Build.VERSION.SDK_INT
import androidx.annotation.RequiresApi
import java.lang.reflect.Method

@RequiresApi(21)
class RippleDrawableCompat(
    color: ColorStateList,
    content: Drawable?,
    mask: Drawable?,
) : RippleDrawable(color, content, mask) {

    @TargetApi(21)
    override fun isProjected(): Boolean {
        return DELEGATE.isProjected(this)
    }

    @TargetApi(21)
    override fun getRadius(): Int {
        return DELEGATE.getRadius(this)
    }

    @TargetApi(21)
    override fun setRadius(radius: Int) {
        DELEGATE.setRadius(this, radius)
    }

    private companion object {

        val DELEGATE = when {
            SDK_INT >= 23 -> RippleDrawableApi23
            else -> RippleDrawableApi21
        }
    }
}

@RequiresApi(21)
private interface RippleDrawableApi {

    fun getRadius(d: RippleDrawable): Int

    fun setRadius(d: RippleDrawable, radius: Int)

    fun isProjected(d: RippleDrawable): Boolean
}

@RequiresApi(21)
@SuppressLint("PrivateApi")
private object RippleDrawableApi21 : RippleDrawableApi {

    private val getMaxRadius: Method = RippleDrawable::class.java
        .getDeclaredMethod("getMaxRadius")

    private val setMaxRadius: Method = RippleDrawable::class.java
        .getDeclaredMethod("setMaxRadius", Int::class.javaPrimitiveType)

    override fun getRadius(d: RippleDrawable): Int {
        return getMaxRadius.invoke(d) as Int
    }

    override fun setRadius(d: RippleDrawable, radius: Int) {
        setMaxRadius.invoke(d, radius)
        d.invalidateSelf()
    }

    override fun isProjected(d: RippleDrawable): Boolean {
        // If the layer is bounded, then we don't need to project.
        if (isBounded(d)) {
            return false
        }

        // Otherwise, if the maximum radius is contained entirely within the
        // bounds then we don't need to project. This is sort of a hack to
        // prevent check box ripples from being projected across the edges of
        // scroll views. It does not impact rendering performance, and it can
        // be removed once we have better handling of projection in scrollable
        // views.
        val radius = getRadius(d)
        val drawableBounds = d.bounds
        val hotspotBounds = getHotspotBounds(d)
        if (radius != RADIUS_AUTO
            && radius <= hotspotBounds.width() / 2
            && radius <= hotspotBounds.height() / 2
            && (drawableBounds == hotspotBounds || drawableBounds.contains(hotspotBounds))
        ) {
            return false
        }

        return true
    }

    private fun isBounded(d: RippleDrawable): Boolean {
        return d.numberOfLayers > 0
    }

    @TargetApi(23)
    private fun getHotspotBounds(d: RippleDrawable): Rect {
        return tempRect.also(d::getHotspotBounds)
    }

    private val tempRect = Rect()
}

@RequiresApi(23)
private object RippleDrawableApi23 : RippleDrawableApi {

    override fun getRadius(d: RippleDrawable): Int {
        return d.radius
    }

    override fun setRadius(d: RippleDrawable, radius: Int) {
        d.radius = radius
    }

    @TargetApi(29)
    override fun isProjected(d: RippleDrawable): Boolean {
        return d.isProjected
    }
}
