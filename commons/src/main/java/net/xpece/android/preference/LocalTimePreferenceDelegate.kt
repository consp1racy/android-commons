package net.xpece.android.preference

import android.content.SharedPreferences
import org.jetbrains.annotations.Contract
import org.threeten.bp.LocalTime

class LocalTimePreferenceDelegate(prefs: SharedPreferences, key: String, default: LocalTime? = null) :
        PrintablePreferenceDelegate<LocalTime>(prefs, key, default) {
    @Contract("null -> null")
    override fun fromString(input: String?) = input?.run { LocalTime.parse(this) }
}
