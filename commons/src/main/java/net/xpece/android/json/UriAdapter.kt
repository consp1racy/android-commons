package net.xpece.android.json

import android.net.Uri

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

/**
 * @author Eugen on 27.08.2016.
 */

object UriAdapter {
    @ToJson
    fun toJson(uri: Uri): String {
        return uri.toString()
    }

    @FromJson
    fun fromJson(uri: String): Uri {
        return Uri.parse(uri)
    }
}
