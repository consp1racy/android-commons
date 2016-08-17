package com.squareup.picasso

import android.graphics.Bitmap
import net.xpece.android.graphics.rotate

/**
 * @author Eugen on 13. 5. 2016.
 */
class RotateTransformation(private val portrait: Boolean) : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        if (source.height < source.width && portrait) {
            try {
                return source.rotate(90F)
            } finally {
                source.recycle()
            }
        } else if (source.height > source.width && !portrait) {
            try {
                return source.rotate(90F)
            } finally {
                source.recycle()
            }
        } else {
            return source
        }
    }

    override fun key(): String {
        return "${TAG}{portrait=$portrait}"
    }

    companion object {
        private val TAG = RotateTransformation::class.java.simpleName
    }

}
