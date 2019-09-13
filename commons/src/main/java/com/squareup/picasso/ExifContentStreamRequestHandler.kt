package com.squareup.picasso

import android.content.ContentResolver
import android.content.Context
import net.xpece.android.media.exif.ExifUtils
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

/**
 * Requires AndroidX Exif Interface.
 *
 * *Warning!* Android PNG reader doesn't support reset stream. Open a new stream before reading the image.
 */
open class ExifContentStreamRequestHandler(val context: Context) : RequestHandler() {

    override fun canHandleRequest(data: Request): Boolean {
        return ContentResolver.SCHEME_CONTENT == data.uri.scheme
    }

    @Throws(IOException::class)
    override fun load(request: Request, networkPolicy: Int): Result {
        val inputStream = getInputStream(request).buffered()

        val orientation = ExifUtils.getOrientation(inputStream)
        val rotation = ExifUtils.getRotationFromOrientation(orientation)

        // Package private, do not move.
        return Result(null, inputStream, Picasso.LoadedFrom.DISK, rotation)
    }

    @Throws(FileNotFoundException::class)
    open fun getInputStream(request: Request): InputStream {
        val contentResolver = context.contentResolver
        return contentResolver.openInputStream(request.uri)
                ?: throw FileNotFoundException("Couldn't open ${request.uri}.")
    }

}
