package net.xpece.android.preference

import android.content.SharedPreferences
import org.threeten.bp.OffsetDateTime

class OffsetDateTimePreferenceDelegate(
        prefs: SharedPreferences, key: String, default: OffsetDateTime? = null) :
        PrintablePreferenceDelegate<OffsetDateTime>(prefs, key, default) {
    override fun fromString(input: String) = OffsetDateTime.parse(input)!!
}
