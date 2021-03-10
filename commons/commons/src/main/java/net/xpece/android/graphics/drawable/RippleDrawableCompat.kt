package net.xpece.android.graphics.drawable

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.content.res.TypedArray
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.RippleDrawable.RADIUS_AUTO
import android.os.Build.VERSION.SDK_INT
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.lang.reflect.Method

@RequiresApi(21)
class RippleDrawableCompat(
    color: ColorStateList,
    content: Drawable?,
    mask: Drawable?,
) : RippleDrawable(color, content, mask) {

    internal constructor() : this(ColorStateList.valueOf(0), null, null)

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

    @Throws(XmlPullParserException::class, IOException::class)
    override fun inflate(r: Resources, parser: XmlPullParser, attrs: AttributeSet, theme: Theme?) {
        DELEGATE.inflate(this, r, parser, attrs, theme)
    }

    internal fun superInflate(
        r: Resources,
        parser: XmlPullParser,
        attrs: AttributeSet,
        theme: Theme?
    ) {
        super.inflate(r, parser, attrs, theme)
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

    fun getRadius(d: RippleDrawableCompat): Int

    fun setRadius(d: RippleDrawableCompat, radius: Int)

    fun isProjected(d: RippleDrawableCompat): Boolean

    @Throws(XmlPullParserException::class, IOException::class)
    fun inflate(
        d: RippleDrawableCompat,
        r: Resources,
        parser: XmlPullParser,
        attrs: AttributeSet,
        theme: Theme?,
    )
}

@RequiresApi(21)
@SuppressLint("PrivateApi")
private object RippleDrawableApi21 : RippleDrawableApi {

    private val getMaxRadius: Method = RippleDrawable::class.java
        .getDeclaredMethod("getMaxRadius")

    private val setMaxRadius: Method = RippleDrawable::class.java
        .getDeclaredMethod("setMaxRadius", Int::class.javaPrimitiveType)

    override fun getRadius(d: RippleDrawableCompat): Int {
        return getMaxRadius.invoke(d) as Int
    }

    override fun setRadius(d: RippleDrawableCompat, radius: Int) {
        setMaxRadius.invoke(d, radius)
        d.invalidateSelf()
    }

    override fun isProjected(d: RippleDrawableCompat): Boolean {
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

    private fun isBounded(d: RippleDrawableCompat): Boolean {
        return d.numberOfLayers > 0
    }

    private val TEMP_RECT = Rect()

    @TargetApi(23)
    private fun getHotspotBounds(d: RippleDrawableCompat): Rect {
        return TEMP_RECT.also(d::getHotspotBounds)
    }

    private val ATTRS = intArrayOf(android.R.attr.radius)

    @Throws(XmlPullParserException::class, IOException::class)
    override fun inflate(
        d: RippleDrawableCompat,
        r: Resources,
        parser: XmlPullParser,
        attrs: AttributeSet,
        theme: Theme?
    ) {
        val a = obtainAttributes(r, theme, attrs, ATTRS)
        val radius = try {
            a.getDimensionPixelSize(0, getRadius(d))
        } finally {
            a.recycle()
        }
        d.superInflate(r, parser, attrs, theme)
        setRadius(d, radius)
    }

    @SuppressLint("Recycle")
    private fun obtainAttributes(
        res: Resources,
        theme: Theme?,
        set: AttributeSet,
        attrs: IntArray
    ): TypedArray {
        return theme?.obtainStyledAttributes(set, attrs, 0, 0)
            ?: res.obtainAttributes(set, attrs)
    }
}

@RequiresApi(23)
private object RippleDrawableApi23 : RippleDrawableApi {

    override fun getRadius(d: RippleDrawableCompat): Int {
        return d.radius
    }

    override fun setRadius(d: RippleDrawableCompat, radius: Int) {
        d.radius = radius
    }

    @TargetApi(29)
    override fun isProjected(d: RippleDrawableCompat): Boolean {
        return d.isProjected
    }

    @Throws(XmlPullParserException::class, IOException::class)
    override fun inflate(
        d: RippleDrawableCompat,
        r: Resources,
        parser: XmlPullParser,
        attrs: AttributeSet,
        theme: Theme?
    ) {
        d.superInflate(r, parser, attrs, theme)
    }
}
