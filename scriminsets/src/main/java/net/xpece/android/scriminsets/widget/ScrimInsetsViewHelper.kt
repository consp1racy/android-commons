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

    private var drawTopLeftInsetForeground = true
    private var drawLeftInsetForeground = true
    private var drawBottomLeftInsetForeground = true

    private var drawTopInsetForeground = true

    private var drawTopRightInsetForeground = true
    private var drawRightInsetForeground = true
    private var drawBottomRightInsetForeground = true

    private var drawBottomInsetForeground = true

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

        val scrimInsets = a.getBoolean(R.styleable.ScrimInsetsViewHelper_scrimInsets, false)
        val scrimInsetsHorizontal =
            a.getBoolean(R.styleable.ScrimInsetsViewHelper_scrimInsetsHorizontal, false)
        val scrimInsetsVertical =
            a.getBoolean(R.styleable.ScrimInsetsViewHelper_scrimInsetsVertical, false)
        val scrimInsetLeft = a.getBoolean(R.styleable.ScrimInsetsViewHelper_scrimInsetLeft, false)
        val scrimInsetTop = a.getBoolean(R.styleable.ScrimInsetsViewHelper_scrimInsetTop, false)
        val scrimInsetRight = a.getBoolean(R.styleable.ScrimInsetsViewHelper_scrimInsetRight, false)
        val scrimInsetBottom =
            a.getBoolean(R.styleable.ScrimInsetsViewHelper_scrimInsetBottom, false)
        setDrawLeftInsetForeground(scrimInsetLeft || scrimInsetsHorizontal || scrimInsets)
        setDrawTopInsetForeground(scrimInsetTop || scrimInsetsVertical || scrimInsets)
        setDrawRightInsetForeground(scrimInsetRight || scrimInsetsHorizontal || scrimInsets)
        setDrawBottomInsetForeground(scrimInsetBottom || scrimInsetsVertical || scrimInsets)

        a.recycle()
    }

    fun onApplyWindowInsets(insets: WindowInsetsCompat) {
        if (this.insets == null) {
            this.insets = Rect()
        }

        this.insets!!.set(
            insets.systemWindowInsetLeft,
            insets.systemWindowInsetTop,
            insets.systemWindowInsetRight,
            insets.systemWindowInsetBottom
        )

        view.setWillNotDraw(!insets.hasSystemWindowInsets() || insetForeground == null)
        ViewCompat.postInvalidateOnAnimation(view)
    }

    fun setScrimInsetForeground(drawable: Drawable?) {
        this.insetForeground = drawable
    }

    fun setDrawLeftInsetForeground(drawLeftInsetForeground: Boolean) {
        this.drawLeftInsetForeground = drawLeftInsetForeground
        setDrawTopLeftInsetForeground(drawLeftInsetForeground)
        setDrawBottomLeftInsetForeground(drawLeftInsetForeground)
    }

    fun setDrawTopInsetForeground(drawTopInsetForeground: Boolean) {
        this.drawTopInsetForeground = drawTopInsetForeground
    }

    fun setDrawRightInsetForeground(drawRightInsetForeground: Boolean) {
        this.drawRightInsetForeground = drawRightInsetForeground
        setDrawTopRightInsetForeground(drawRightInsetForeground)
        setDrawBottomRightInsetForeground(drawRightInsetForeground)
    }

    fun setDrawBottomInsetForeground(drawBottomInsetForeground: Boolean) {
        this.drawBottomInsetForeground = drawBottomInsetForeground
    }

    private fun setDrawTopLeftInsetForeground(drawTopLeftInsetForeground: Boolean) {
        this.drawTopLeftInsetForeground = drawTopLeftInsetForeground
    }

    private fun setDrawTopRightInsetForeground(drawTopRightInsetForeground: Boolean) {
        this.drawTopRightInsetForeground = drawTopRightInsetForeground
    }

    private fun setDrawBottomLeftInsetForeground(drawBottomLeftInsetForeground: Boolean) {
        this.drawBottomLeftInsetForeground = drawBottomLeftInsetForeground
    }

    private fun setDrawBottomRightInsetForeground(drawBottomRightInsetForeground: Boolean) {
        this.drawBottomRightInsetForeground = drawBottomRightInsetForeground
    }

    fun draw(canvas: Canvas) {
        val insets = insets ?: return
        val insetForeground = insetForeground ?: return

        val width = view.width
        val height = view.height

        val sc = canvas.save()
        canvas.translate(view.scrollX.toFloat(), view.scrollY.toFloat())

        if (drawTopLeftInsetForeground) {
            tempRect.set(0, 0, insets.left, insets.top)
            if (!tempRect.isEmpty) {
                insetForeground.bounds = tempRect
                insetForeground.draw(canvas)
            }
        }

        if (drawTopInsetForeground) {
            tempRect.set(insets.left, 0, width - insets.right, insets.top)
            if (!tempRect.isEmpty) {
                insetForeground.bounds = tempRect
                insetForeground.draw(canvas)
            }
        }

        if (drawTopRightInsetForeground) {
            tempRect.set(width - insets.right, 0, width, insets.top)
            if (!tempRect.isEmpty) {
                insetForeground.bounds = tempRect
                insetForeground.draw(canvas)
            }
        }

        if (drawLeftInsetForeground) {
            tempRect.set(0, insets.top, insets.left, height - insets.bottom)
            if (!tempRect.isEmpty) {
                insetForeground.bounds = tempRect
                insetForeground.draw(canvas)
            }
        }

        if (drawRightInsetForeground) {
            tempRect.set(width - insets.right, insets.top, width, height - insets.bottom)
            if (!tempRect.isEmpty) {
                insetForeground.bounds = tempRect
                insetForeground.draw(canvas)
            }
        }

        if (drawBottomLeftInsetForeground) {
            tempRect.set(0, height - insets.bottom, insets.left, height)
            if (!tempRect.isEmpty) {
                insetForeground.bounds = tempRect
                insetForeground.draw(canvas)
            }
        }

        if (drawBottomInsetForeground) {
            tempRect.set(insets.left, height - insets.bottom, width - insets.right, height)
            if (!tempRect.isEmpty) {
                insetForeground.bounds = tempRect
                insetForeground.draw(canvas)
            }
        }

        if (drawBottomRightInsetForeground) {
            tempRect.set(width - insets.right, height - insets.bottom, width, height)
            if (!tempRect.isEmpty) {
                insetForeground.bounds = tempRect
                insetForeground.draw(canvas)
            }
        }

        canvas.restoreToCount(sc)
    }

    fun onAttachedToWindow() {
        insetForeground?.callback = view
    }

    fun onDetachedFromWindow() {
        insetForeground?.callback = null
    }
}
