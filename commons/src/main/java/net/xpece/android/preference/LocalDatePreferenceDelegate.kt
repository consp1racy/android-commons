package net.xpece.android.preference

import android.content.SharedPreferences
import org.threeten.bp.LocalDate

class LocalDatePreferenceDelegate(prefs: SharedPreferences, key: String, default: LocalDate? = null) :
        PrintablePreferenceDelegate<LocalDate>(prefs, key, default) {
    override fun fromString(input: String?) = input?.run { LocalDate.parse(this) }
}
