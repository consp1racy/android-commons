package net.xpece.android.media.exif

import android.content.Context
import android.net.Uri
import java.io.BufferedInputStream

/**
 * Created by Eugen on 19.05.2016.
 */

object ExifUtils {

    @JvmStatic
    fun getOrientation(context: Context, uri: Uri): Int {
        context.contentResolver.openInputStream(uri).buffered().use {
            val orientation = getOrientation(it)
            return orientation
        }
    }

    /**
     * Android PNG reader doesn't support reset stream. Open a new stream before reading the image.
     */
    @JvmStatic
    fun getOrientation(buffered: BufferedInputStream): Int {
        var orientation = 0
        val exif = ExifInterface()
        try {
            buffered.mark(65535)
            exif.readExif(buffered)
            orientation = exif.getTagIntValue(ExifInterface.TAG_ORIENTATION) ?: 0
            buffered.reset()
        } catch (ex: Exception) {
//        Timber.e(ex, "Invalid EXIF format")
        }
        return orientation
    }

    /**
     * Get rotation in degrees from EXIF tag.
     */
    @JvmStatic
    fun getRotationFromOrientation(orientation: Int): Int {
        return ExifInterface.getRotationForOrientationValue(orientation.toShort())
    }
}
