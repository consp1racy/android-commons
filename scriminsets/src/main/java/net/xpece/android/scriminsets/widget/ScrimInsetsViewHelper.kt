package net.xpece.android.scriminsets.widget

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.xpece.android.content.getDrawableCompat
import net.xpece.android.scriminsets.R

class ScrimInsetsViewHelper(private val view: View) {

    private var insetForeground: Drawable? = null

    private var insets: Rect? = null

    private var drawLeftInsetForeground = true
    private var drawTopInsetForeground = true
    private var drawRightInsetForeground = true
    private var drawBottomInsetForeground = true

    private var consumeInsets = true

    private val tempRect = Rect()

    fun loadFromAttributes(
            attrs: AttributeSet?,
            @AttrRes defStyleAttr: Int,
            @StyleRes defStyleRes: Int
    ) {
        // Short-circuit if the platform doesn't support drawing behind insets.
        if (Build.VERSION.SDK_INT < 21) return

        val c = view.context
        val a = c.obtainStyledAttributes(
                attrs,
                R.styleable.ScrimInsetsViewHelper,
                defStyleAttr,
                defStyleRes
        )
        insetForeground = a.getDrawableCompat(c, R.styleable.ScrimInsetsViewHelper_insetForeground)
        consumeInsets = a.getBoolean(R.styleable.ScrimInsetsViewHelper_consumeInsets, true)
        a.recycle()
    }

    fun onApplyWindowInsets(insets: WindowInsetsCompat): WindowInsetsCompat {
        if (this.insets == null) {
            this.insets = Rect()
        }

        this.insets!!.set(
                insets.systemWindowInsetLeft,
                insets.systemWindowInsetTop,
                insets.systemWindowInsetRight,
                insets.systemWindowInsetBottom)

        view.setWillNotDraw(!insets.hasSystemWindowInsets() || insetForeground == null)
        ViewCompat.postInvalidateOnAnimation(view)

        return if (consumeInsets) {
            insets.consumeSystemWindowInsets()
        } else {
            insets
        }
    }

    fun setScrimInsetForeground(drawable: Drawable?) {
        this.insetForeground = drawable
    }

    fun setDrawLeftInsetForeground(drawLeftInsetForeground: Boolean) {
        this.drawLeftInsetForeground = drawLeftInsetForeground
    }

    fun setDrawTopInsetForeground(drawTopInsetForeground: Boolean) {
        this.drawTopInsetForeground = drawTopInsetForeground
    }

    fun setDrawRightInsetForeground(drawRightInsetForeground: Boolean) {
        this.drawRightInsetForeground = drawRightInsetForeground
    }

    fun setDrawBottomInsetForeground(drawBottomInsetForeground: Boolean) {
        this.drawBottomInsetForeground = drawBottomInsetForeground
    }

    fun draw(canvas: Canvas) {
        val insets = insets
        val insetForeground = insetForeground
        if (insets != null && insetForeground != null) {
            val width = view.width
            val height = view.height

            val sc = canvas.save()
            canvas.translate(view.scrollX.toFloat(), view.scrollY.toFloat())

            if (drawTopInsetForeground) {
                tempRect.set(0, 0, width, insets.top)
                insetForeground.bounds = tempRect
                insetForeground.draw(canvas)
            }

            if (drawBottomInsetForeground) {
                tempRect.set(0, height - insets.bottom, width, height)
                insetForeground.bounds = tempRect
                insetForeground.draw(canvas)
            }

            if (drawLeftInsetForeground) {
                tempRect.set(0, insets.top, insets.left, height - insets.bottom)
                insetForeground.bounds = tempRect
                insetForeground.draw(canvas)
            }

            if (drawRightInsetForeground) {
                tempRect.set(width - insets.right, insets.top, width, height - insets.bottom)
                insetForeground.bounds = tempRect
                insetForeground.draw(canvas)
            }

            canvas.restoreToCount(sc)
        }
    }

    fun onAttachedToWindow() {
        insetForeground?.callback = view
    }

    fun onDetachedFromWindow() {
        insetForeground?.callback = null
    }
}
