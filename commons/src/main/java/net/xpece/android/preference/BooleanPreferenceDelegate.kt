package net.xpece.android.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.reflect.KProperty

class BooleanPreferenceDelegate(val prefs: SharedPreferences, val key: String, val default: Boolean = false) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return prefs.getBoolean(key, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        prefs.edit { putBoolean(key, value) }
    }
}
