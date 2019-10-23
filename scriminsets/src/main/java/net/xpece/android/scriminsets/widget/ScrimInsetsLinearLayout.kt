package net.xpece.android.scriminsets.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.CallSuper
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.xpece.android.content.resolveResourceId
import net.xpece.android.scriminsets.R

open class ScrimInsetsLinearLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = R.attr.scrimInsetsLinearLayoutStyle
) : LinearLayout(context, attrs, defStyleAttr) {

    private val helper = ScrimInsetsViewHelper(this).apply {
        @Suppress("NAME_SHADOWING")
        var defStyleAttr = defStyleAttr
        if (context.resolveResourceId(R.attr.scrimInsetsLinearLayoutStyle, 0) == 0) {
            // If the new attr is not defined fall back to the old attr.
            defStyleAttr = R.attr.scrimInsetLinearLayoutStyle
        }
        val defStyleRes = R.style.Widget_Xpece_ScrimInsetsLinearLayout
        loadFromAttributes(attrs, defStyleAttr, defStyleRes)
    }

    init {
        // No need to draw until the insets are adjusted
        setWillNotDraw(true)

        ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
            onApplyWindowInsetsCompat(insets)
        }
    }

    /**
     * 1. Call from custom [OnApplyWindowInsetsListener] to update scrim insets.
     * 2. Override this method in subclasses to add custom behavior.
     * You're responsible for consuming the insets.
     */
    @CallSuper
    open fun onApplyWindowInsetsCompat(insets: WindowInsetsCompat): WindowInsetsCompat {
        onInsetsChanged(insets)
        helper.onApplyWindowInsets(insets)
        return insets
    }

    protected open fun onInsetsChanged(insets: WindowInsetsCompat) {
    }

    fun setScrimInsetForeground(drawable: Drawable?) {
        helper.setScrimInsetForeground(drawable)
    }

    fun setDrawLeftInsetForeground(drawLeftInsetForeground: Boolean) {
        helper.setDrawLeftInsetForeground(drawLeftInsetForeground)
    }

    fun setDrawTopInsetForeground(drawTopInsetForeground: Boolean) {
        helper.setDrawTopInsetForeground(drawTopInsetForeground)
    }

    fun setDrawRightInsetForeground(drawRightInsetForeground: Boolean) {
        helper.setDrawRightInsetForeground(drawRightInsetForeground)
    }

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
