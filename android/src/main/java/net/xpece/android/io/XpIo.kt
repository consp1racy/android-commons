@file:JvmName("XpIo")

package net.xpece.android.io

import android.content.Context
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*

fun Context.copy(inputUri: Uri, outputUri: Uri) {
    val input = contentResolver.openInputStream(inputUri)
    val output = contentResolver.openOutputStream(outputUri)

    input.copyTo(output)
    output.flush()

//    val source = Okio.buffer(Okio.source(input))
//    val sink = Okio.buffer(Okio.sink(output))
//
//    try {
//        sink.writeAll(source)
//        sink.flush()
//    } finally {
//        sink.close()
//        source.close()
//    }
}

fun InputStream.readToString(charset: String = "UTF-8"): String {
    return Scanner(this, charset).useDelimiter("\\A").next()
}

fun InputStream.bytes(): ByteArray {
    ByteArrayOutputStream().use {
        this.copyTo(it)
        return it.toByteArray()
    }
}
