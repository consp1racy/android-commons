@file:JvmName("XpIo")

package net.xpece.android.io

import android.content.Context
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*

fun Context.copy(inputUri: Uri, outputUri: Uri) {
    contentResolver.openInputStream(inputUri).use { input ->
        contentResolver.openOutputStream(outputUri).use { output ->
            input.copyTo(output)
        }
    }
}

@JvmOverloads
fun InputStream.readToString(charset: String = "UTF-8"): String {
    return Scanner(this, charset).useDelimiter("\\A").next()
}

fun InputStream.bytes(): ByteArray {
    ByteArrayOutputStream().use {
        this.copyTo(it)
        return it.toByteArray()
    }
}
