package net.xpece.android.net

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

@Deprecated(
    "Use AndroidX.",
    ReplaceWith("toUri()", imports = ["androidx.core.net.toUri"])
)
fun String?.toUri() = if (this != null) Uri.parse(this) else null

/**
 * http://alvarestech.com/temp/routeconverter/RouteConverter/navigation-formats/src/main/doc/googlemaps/Google_Map_Parameters.htm
 */
fun getWebMapUri(query: String, latLng: LatLng?) =
        Uri.parse("https://maps.google.com/maps").buildUpon()
                .appendQueryParameter("q", query)
                .appendQueryParameterOptional("ll", latLng) { "${it.latitude},${it.longitude}" }
                .build()!!

fun <T> Uri.Builder.appendQueryParameterOptional(key: String, value: T?, transform: (T) -> String = { it.toString() }) = apply {
    if (value != null) {
        val transformed = transform.invoke(value)
        appendQueryParameter(key, transformed)
    }
}
