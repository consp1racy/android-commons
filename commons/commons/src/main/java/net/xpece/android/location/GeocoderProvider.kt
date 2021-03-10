package net.xpece.android.location

import android.content.Context
import android.location.Geocoder
import java.util.*

interface GeocoderProvider {
    @Throws(GeocoderNotAvailableException::class)
    fun getGeocoder(context: Context, @Suppress("DEPRECATION") locale: Locale = context.resources.configuration.locale): Geocoder

    val isGeocoderPresent: Boolean
}
