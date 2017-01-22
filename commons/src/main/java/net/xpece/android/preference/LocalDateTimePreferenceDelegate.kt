package net.xpece.android.preference

import android.content.SharedPreferences
import org.jetbrains.annotations.Contract
import org.threeten.bp.LocalDateTime

class LocalDateTimePreferenceDelegate(prefs: SharedPreferences, key: String, default: LocalDateTime? = null) :
        PrintablePreferenceDelegate<LocalDateTime>(prefs, key, default) {
    @Contract("null -> null")
    override fun fromString(input: String?) = input?.run { LocalDateTime.parse(this) }
}
