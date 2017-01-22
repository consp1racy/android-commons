package net.xpece.android.net

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import net.xpece.android.database.getLong
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Okio
import java.io.IOException

/** Request body that transmits the content of [Uri].  */
class UriRequestBody(context: Context, val uri: Uri) : RequestBody() {
    private val context: Context

    init {
        this.context = context.applicationContext
    }

    override fun contentType(): MediaType {
        val mime = context.contentResolver.getType(uri)
        return MediaType.parse(mime)
    }

    override fun contentLength(): Long {
        context.contentResolver.query(uri, arrayOf(OpenableColumns.SIZE), null, null, null)?.use {
            if (it.moveToFirst()) {
                val size = it.getLong(0, -1L)
                return size
            }
        }
        return -1
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        context.contentResolver.openInputStream(uri)?.use {
            val source = Okio.source(it)
            sink.writeAll(source)
        }
    }
}
