package net.xpece.android.location

import android.content.Context
import android.location.Geocoder
import java.util.*

/**
 * @author Eugen on 20.12.2016.
 */
class GeocoderProviderImpl : GeocoderProvider {
    companion object {
        @JvmStatic
        private val cache = WeakHashMap<Key, Geocoder>()
    }

    @Synchronized
    @Throws(GeocoderNotAvailableException::class)
    override fun getGeocoder(context: Context, locale: Locale): Geocoder {
        if (Geocoder.isPresent()) {
            @Suppress("NAME_SHADOWING")
            val context = context.applicationContext
            val key = Key(context, locale)
            var geocoder = cache[key]
            if (geocoder == null) {
                geocoder = Geocoder(context, locale)
                cache[key] = geocoder
            }
            return geocoder
        }
        throw GeocoderNotAvailableException()
    }

    override val isGeocoderPresent: Boolean
        get() = Geocoder.isPresent()

    internal data class Key(val context: Context, val locale: Locale)
}
