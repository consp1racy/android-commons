package net.xpece.android.scriminsets.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.xpece.android.scriminsets.R

open class ScrimInsetsFrameLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val helper = ScrimInsetsViewHelper(this).apply {
        val defStyleRes = R.style.Widget_Xpece_ScrimInsetsFrameLayout
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
        return helper.onApplyWindowInsets(insets)
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

    fun setDrawTopLeftInsetForeground(drawTopLeftInsetForeground: Boolean) {
        helper.setDrawTopLeftInsetForeground(drawTopLeftInsetForeground)
    }

    fun setDrawTopRightInsetForeground(drawTopRightInsetForeground: Boolean) {
        helper.setDrawTopRightInsetForeground(drawTopRightInsetForeground)
    }

    fun setDrawBottomLeftInsetForeground(drawBottomLeftInsetForeground: Boolean) {
        helper.setDrawBottomLeftInsetForeground(drawBottomLeftInsetForeground)
    }

    fun setDrawBottomRightInsetForeground(drawBottomRightInsetForeground: Boolean) {
        helper.setDrawBottomRightInsetForeground(drawBottomRightInsetForeground)
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
