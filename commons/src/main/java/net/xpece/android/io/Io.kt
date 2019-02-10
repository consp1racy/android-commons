@file:JvmName("XpIo")

package net.xpece.android.io

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*

@Deprecated("Use ContentResolver.copy instead.", replaceWith = ReplaceWith("contentResolver.copy"))
fun Context.copy(inputUri: Uri, outputUri: Uri) {
    contentResolver.copy(inputUri, outputUri)
}

fun ContentResolver.copy(inputUri: Uri, outputUri: Uri) {
    openInputStream(inputUri).use { input ->
        openOutputStream(outputUri).use { output ->
            input.copyTo(output)
        }
    }
}

@JvmOverloads
fun InputStream.string(charset: String = "UTF-8"): String =
        Scanner(this, charset).useDelimiter("\\A").next()

fun InputStream.bytes(): ByteArray = ByteArrayOutputStream().use {
    this.copyTo(it)
    return it.toByteArray()
}
