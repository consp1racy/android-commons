/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.xpece.android.widget

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.appcompat.widget.TintTypedArray
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.xpece.android.R

class ScrimInsetsViewHelper(private val view: View) {

    private var insetForeground: Drawable? = null

    private var insets: Rect? = null

    private var drawLeftInsetForeground = true
    private var drawTopInsetForeground = true
    private var drawRightInsetForeground = true
    private var drawBottomInsetForeground = true

    private var consumeInsets = true

    private val tempRect = Rect()

    fun loadFromAttributes(attrs: AttributeSet?, @AttrRes defStyleAttr: Int) {
        // Short-circuit if the platform doesn't support drawing behind insets.
        if (Build.VERSION.SDK_INT < 21) return

        val a = TintTypedArray.obtainStyledAttributes(
                view.context,
                attrs,
                R.styleable.ScrimInsetsViewHelper,
                defStyleAttr,
                R.style.Widget_Design_ScrimInsetsFrameLayout
        )
        insetForeground = a.getDrawable(R.styleable.ScrimInsetsViewHelper_insetForeground)
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
