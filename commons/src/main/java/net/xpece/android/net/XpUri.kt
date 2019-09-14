@file:JvmName("XpUri")

package net.xpece.android.net

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

/**
 * http://alvarestech.com/temp/routeconverter/RouteConverter/navigation-formats/src/main/doc/googlemaps/Google_Map_Parameters.htm
 */
fun getWebMapUri(query: String, latLng: LatLng?) =
        Uri.parse("https://maps.google.com/maps").buildUpon()
                .appendQueryParameter("q", query)
                .appendQueryParameterOptional("ll", latLng) { "${it.latitude},${it.longitude}" }
                .build()!!

@JvmSynthetic
inline fun <T> Uri.Builder.appendQueryParameterOptional(key: String, value: T?, transform: (T) -> String = { it.toString() }) = apply {
    if (value != null) {
        val transformed = transform.invoke(value)
        appendQueryParameter(key, transformed)
    }
}
