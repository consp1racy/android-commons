package net.xpece.android.scriminsets.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.annotation.CallSuper
import androidx.annotation.StyleRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.xpece.android.scriminsets.R

/**
 * A [LinearLayout] with the ability to scrim window insets.
 *
 * @param context The Context the view is running in, through which it can
 *        access the current theme, resources, etc.
 * @param attrs The attributes of the XML tag that is inflating the view.
 * @param defStyleAttr The resource identifier of an attribute in the current theme
 *        whose value is the the resource id of a style.
 *        The specified style’s attribute values serve as default values for the view.
 *        Set this parameter to 0 to avoid use of default values.
 * @param defStyleRes The identifier of a style resource that supplies default values
 *        for the view, used only if [defStyleAttr] is 0 or cannot be found in the theme.
 *        Set this parameter to 0 to avoid use of default values.
 *
 * @see R.attr.insetForeground
 * @see R.attr.scrimInsets
 * @see R.attr.scrimInsetsHorizontal
 * @see R.attr.scrimInsetsVertical
 * @see R.attr.scrimInsetLeft
 * @see R.attr.scrimInsetTop
 * @see R.attr.scrimInsetRight
 * @see R.attr.scrimInsetBottom
 */
@Suppress("LeakingThis")
open class ScrimInsetsLinearLayout
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.scrimInsetsLinearLayoutStyle,
    @StyleRes defStyleRes: Int = R.style.Widget_Xpece_ScrimInsetsLinearLayout
) : LinearLayout(context, attrs, defStyleAttr) {

    private val helper = ScrimInsetsViewHelper(this).apply {
        loadFromAttributes(attrs, defStyleAttr, defStyleRes)
    }

    init {
        // No need to draw until the insets are adjusted
        setWillNotDraw(true)

        ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
            onApplyWindowInsetsCompat(insets)
        }
    }

    @CallSuper
    open fun onApplyWindowInsetsCompat(insets: WindowInsetsCompat): WindowInsetsCompat {
        helper.onApplyWindowInsets(insets)
        return insets
    }

    /**
     * Set the drawable used to draw the inset scrims. This should be a color.
     */
    fun setScrimInsetForeground(drawable: Drawable?) {
        helper.setScrimInsetForeground(drawable)
    }

    /**
     * Set whether the left inset scrim should be drawn.
     *
     * Top left and bottom left corner scrims will be drawn if this is `true`.
     */
    fun setDrawLeftInsetForeground(drawLeftInsetForeground: Boolean) {
        helper.setDrawLeftInsetForeground(drawLeftInsetForeground)
    }

    /**
     * Set whether the top inset scrim should be drawn.
     *
     * Top left and top right corner scrims will be drawn if this is `true`.
     */
    fun setDrawTopInsetForeground(drawTopInsetForeground: Boolean) {
        helper.setDrawTopInsetForeground(drawTopInsetForeground)
    }

    /**
     * Set whether the right inset scrim should be drawn.
     *
     * Top right and bottom right corner scrims will be drawn if this is `true`.
     */
    fun setDrawRightInsetForeground(drawRightInsetForeground: Boolean) {
        helper.setDrawRightInsetForeground(drawRightInsetForeground)
    }

    /**
     * Set whether the bottom inset scrim should be drawn.
     *
     * Bottom left and bottom right corner scrims will be drawn if this is `true`.
     */
    fun setDrawBottomInsetForeground(drawBottomInsetForeground: Boolean) {
        helper.setDrawBottomInsetForeground(drawBottomInsetForeground)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        helper.draw(canvas)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        helper.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        helper.onDetachedFromWindow()
        super.onDetachedFromWindow()
    }
}
