package net.xpece.android.preference

import android.content.SharedPreferences
import org.threeten.bp.LocalDateTime

class LocalDateTimePreferenceDelegate(prefs: SharedPreferences, key: String, default: LocalDateTime? = null) :
        PrintablePreferenceDelegate<LocalDateTime>(prefs, key, default) {
    override fun fromString(input: String?) = input?.run { LocalDateTime.parse(this) }
}
