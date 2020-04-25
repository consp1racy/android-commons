@file:JvmName("XpIo")

package net.xpece.android.io

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import java.io.InputStream
import java.nio.charset.Charset

@Deprecated(
    message = "Use ContentResolver.copy instead.",
    replaceWith = ReplaceWith("contentResolver.copy(inputUri, outputUri)", "net.xpece.android.io.copy"),
    level = DeprecationLevel.ERROR
)
fun Context.copy(inputUri: Uri, outputUri: Uri) {
    contentResolver.copy(inputUri, outputUri)
}

fun ContentResolver.copy(inputUri: Uri, outputUri: Uri) {
    openInputStream(inputUri)!!.use { input ->
        openOutputStream(outputUri)!!.use { output ->
            input.copyTo(output)
        }
    }
}

@Deprecated(
    message = "Use standard library.",
    replaceWith = ReplaceWith("readBytes", "kotlin.io.readBytes"),
    level = DeprecationLevel.ERROR
)
@JvmOverloads
fun InputStream.string(charset: String = "UTF-8"): String =
    reader(Charset.forName(charset)).readText()

@Deprecated(
    message = "Use standard library.",
    replaceWith = ReplaceWith("readBytes", "kotlin.io.readBytes"),
    level = DeprecationLevel.ERROR
)
fun InputStream.bytes(): ByteArray = readBytes()
