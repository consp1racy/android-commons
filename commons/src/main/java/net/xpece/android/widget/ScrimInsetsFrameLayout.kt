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

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

open class ScrimInsetsFrameLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val helper = ScrimInsetsViewHelper(this)
            .apply { loadFromAttributes(attrs, defStyleAttr) }

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
        return helper.onApplyWindowInsets(insets)
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
