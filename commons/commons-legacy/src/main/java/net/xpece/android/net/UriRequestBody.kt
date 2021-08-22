package net.xpece.android.net

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import net.xpece.android.database.getLong
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Okio
import java.io.FileNotFoundException
import java.io.IOException

/** Request body that transmits the content of [Uri].  */
class UriRequestBody(
        private val contentResolver: ContentResolver,
        private val uri: Uri
) : RequestBody() {

    @Deprecated("Use more specific constructor.")
    constructor(context: Context, uri: Uri) : this(context.contentResolver, uri)

    override fun contentType(): MediaType {
        val mime = contentResolver.getType(uri)
        return MediaType.parse(mime)
    }

    override fun contentLength(): Long {
        contentResolver.query(uri, arrayOf(OpenableColumns.SIZE), null, null, null)?.use {
            if (it.moveToFirst()) {
                return it.getLong(0, -1L)
            }
        }
        return -1
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val input = contentResolver.openInputStream(uri)
                ?: throw FileNotFoundException("Couldn't open $uri.")
        input.use {
            val source = Okio.source(it)
            sink.writeAll(source)
        }
    }
}
