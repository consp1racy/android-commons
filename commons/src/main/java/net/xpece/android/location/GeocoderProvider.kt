package cz.quickjobs.android.location

import android.content.Context
import android.location.Geocoder
import java.util.*

/**
 * @author Eugen on 20.12.2016.
 */
interface GeocoderProvider {
    @Throws(GeocoderNotAvailableException::class)
    fun getGeocoder(context: Context, locale: Locale = context.resources.configuration.locale): Geocoder

    val isGeocoderPresent: Boolean
}
