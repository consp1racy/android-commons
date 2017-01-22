package net.xpece.android.preference

import android.content.SharedPreferences
import org.jetbrains.annotations.Contract
import org.threeten.bp.LocalDate

class LocalDatePreferenceDelegate(prefs: SharedPreferences, key: String, default: LocalDate? = null) :
        PrintablePreferenceDelegate<LocalDate>(prefs, key, default) {
    @Contract("null -> null")
    override fun fromString(input: String?) = input?.run { LocalDate.parse(this) }
}
