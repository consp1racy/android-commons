package net.xpece.android.text.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ReplacementSpan

import androidx.annotation.IntRange

class SpaceSpan(private val mWidth: Int) : ReplacementSpan() {

    override fun getSize(
            paint: Paint,
            text: CharSequence,
            @IntRange(from = 0) start: Int,
            @IntRange(from = 0) end: Int,
            fm: Paint.FontMetricsInt?
    ): Int {
        return mWidth
    }

    override fun draw(
            canvas: Canvas,
            text: CharSequence,
            @IntRange(from = 0) start: Int,
            @IntRange(from = 0) end: Int,
            x: Float,
            top: Int,
            y: Int,
            bottom: Int,
            paint: Paint
    ) {
    }
}
