package net.xpece.android.scriminsets.widget

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.xpece.android.content.getDrawableCompat
import net.xpece.android.scriminsets.R

/**
 * A helper class used to implement scrim insets feature in custom views and layouts.
 *
 * A minimal implementation that reads attributes from XML could look like this:
 *
 *     public class CustomScrimLayout extends FrameLayout {
 *
 *         private final ScrimInsetsViewHelper helper;
 *
 *         public CustomScrimLayout(@NonNull Context context) {
 *             this(context, null);
 *         }
 *
 *         public CustomScrimLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
 *             super(context, attrs);
 *
 *             helper = new ScrimInsetsViewHelper(this);
 *             helper.loadFromAttributes(attrs, 0, 0);
 *
 *             setWillNotDraw(true);
 *
 *             ViewCompat.setOnApplyWindowInsetsListener(this, new androidx.core.view.OnApplyWindowInsetsListener() {
 *                 @Override
 *                 public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
 *                     helper.onApplyWindowInsets(insets);
 *                     return insets;
 *                 }
 *             });
 *         }
 *
 *         @Override
 *         public void draw(Canvas canvas) {
 *             super.draw(canvas);
 *             helper.draw(canvas);
 *         }
 *
 *         @Override
 *         protected void onAttachedToWindow() {
 *             super.onAttachedToWindow();
 *             helper.onAttachedToWindow();
 *         }
 *
 *         @Override
 *         protected void onDetachedFromWindow() {
 *             helper.onDetachedFromWindow();
 *             super.onDetachedFromWindow();
 *         }
 *     }
 *
 * @param view The view this helper will manage inset scrims for.
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

    /**
     * Call this from the custom view's constructor.
     */
    fun loadFromAttributes(
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int,
        @StyleRes defStyleRes: Int
    ) {
        // Short-circuit if the platform doesn't support drawing behind insets.
        if (SDK_INT < 21) return

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

    /**
     * Call this whenever [WindowInsetsCompat] is dispatched to the view.
     */
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

    /**
     * Set the drawable used to draw the inset scrims. This should be a color.
     */
    fun setScrimInsetForeground(drawable: Drawable?) {
        this.insetForeground = drawable
    }

    /**
     * Set whether the left inset scrim should be drawn.
     *
     * Top left and bottom left corner scrims will be drawn if this is `true`.
     */
    fun setDrawLeftInsetForeground(drawLeftInsetForeground: Boolean) {
        this.drawLeftInsetForeground = drawLeftInsetForeground
        this.drawTopLeftInsetForeground = drawLeftInsetForeground || drawTopInsetForeground
        this.drawBottomLeftInsetForeground = drawLeftInsetForeground || drawBottomInsetForeground
    }

    /**
     * Set whether the top inset scrim should be drawn.
     *
     * Top left and top right corner scrims will be drawn if this is `true`.
     */
    fun setDrawTopInsetForeground(drawTopInsetForeground: Boolean) {
        this.drawTopInsetForeground = drawTopInsetForeground
        this.drawTopLeftInsetForeground = drawTopInsetForeground || drawLeftInsetForeground
        this.drawTopRightInsetForeground = drawTopInsetForeground || drawRightInsetForeground
    }

    /**
     * Set whether the right inset scrim should be drawn.
     *
     * Top right and bottom right corner scrims will be drawn if this is `true`.
     */
    fun setDrawRightInsetForeground(drawRightInsetForeground: Boolean) {
        this.drawRightInsetForeground = drawRightInsetForeground
        this.drawTopRightInsetForeground = drawRightInsetForeground || drawTopInsetForeground
        this.drawBottomRightInsetForeground = drawRightInsetForeground || drawBottomInsetForeground
    }

    /**
     * Set whether the bottom inset scrim should be drawn.
     *
     * Bottom left and bottom right corner scrims will be drawn if this is `true`.
     */
    fun setDrawBottomInsetForeground(drawBottomInsetForeground: Boolean) {
        this.drawBottomInsetForeground = drawBottomInsetForeground
        this.drawBottomLeftInsetForeground = drawBottomInsetForeground || drawLeftInsetForeground
        this.drawBottomRightInsetForeground = drawBottomInsetForeground || drawRightInsetForeground
    }

    /**
     * Call this from [View.draw].
     */
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

    /**
     * Call this from [View.onAttachedToWindow].
     */
    fun onAttachedToWindow() {
        insetForeground?.callback = view
    }

    /**
     * Call this from [View.onDetachedFromWindow].
     */
    fun onDetachedFromWindow() {
        insetForeground?.callback = null
    }
}
