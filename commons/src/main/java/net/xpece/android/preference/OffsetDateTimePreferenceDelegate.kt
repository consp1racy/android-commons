package net.xpece.android.preference

import android.content.SharedPreferences
import org.jetbrains.annotations.Contract
import org.threeten.bp.OffsetDateTime

class OffsetDateTimePreferenceDelegate(prefs: SharedPreferences, key: String, default: OffsetDateTime? = null) :
        PrintablePreferenceDelegate<OffsetDateTime>(prefs, key, default) {
    @Contract("null -> null")
    override fun fromString(input: String?) = input?.run { OffsetDateTime.parse(this) }
}
