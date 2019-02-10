@file:JvmName("XpBitmap")

package net.xpece.android.graphics

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.media.ExifInterface

/**
 * @author Eugen on 28. 5. 2016.
 */

/**
 * @param contrast 0..10 1 is default
 * @param brightness -255..255 0 is default
 * @return new bitmap
 */
@Deprecated("Too high level.")
fun Bitmap.withContrastBrightness(contrast: Float, brightness: Float) : Bitmap {
    val cm = ColorMatrix(floatArrayOf(
            contrast, 0f, 0f, 0f, brightness,
            0f, contrast, 0f, 0f, brightness,
            0f, 0f, contrast, 0f, brightness,
            0f, 0f, 0f, 1f, 0f
    ))

    val ret = Bitmap.createBitmap(width, height, config)

    val canvas = Canvas(ret)

    val paint = Paint()
    paint.colorFilter = ColorMatrixColorFilter(cm)
    canvas.drawBitmap(this, 0f, 0f, paint)

    return ret
}

/**
 * @return new bitmap
 */
fun Bitmap.withColorMatrix(cm : ColorMatrix) : Bitmap {
    val ret = Bitmap.createBitmap(width, height, config)

    val canvas = Canvas(ret)

    val paint = Paint()
    paint.colorFilter = ColorMatrixColorFilter(cm)
    canvas.drawBitmap(this, 0f, 0f, paint)

    return ret
}

/**
 *
 * @param contrast 0..10 1 is default
 * @param brightness -255..255 0 is default
 * @return new bitmap
 */
fun createContrastBrightnessMatrix(contrast: Float, brightness: Float) : ColorMatrix {
    val cm = ColorMatrix(floatArrayOf(
            contrast, 0f, 0f, 0f, brightness,
            0f, contrast, 0f, 0f, brightness,
            0f, 0f, contrast, 0f, brightness,
            0f, 0f, 0f, 1f, 0f
    ))
    return cm
}

fun Matrix.applyOrientation(orientation: Int): Matrix {
    when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> postRotate(90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> postRotate(180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> postRotate(270f)
        ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> postScale(-1f, 1f)
        ExifInterface.ORIENTATION_FLIP_VERTICAL -> postScale(1f, -1f)
        ExifInterface.ORIENTATION_TRANSPOSE -> {
            // flipped about top-left <--> bottom-right axis
            postRotate(90f)
            postScale(-1f, 1f)
        }
        ExifInterface.ORIENTATION_TRANSVERSE -> {
            // flipped about top-right <--> bottom-left axis
            postRotate(90f)
            postScale(1f, -1f)
        }
    }
    return this
}

fun Bitmap.rotate(angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}
