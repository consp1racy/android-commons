package net.xpece.android.preference

import android.content.SharedPreferences
import net.xpece.android.content.update
import kotlin.reflect.KProperty

class BooleanPreferenceDelegate(val prefs: SharedPreferences, val key: String, val default: Boolean = false) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        return prefs.getBoolean(key, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        prefs.update { putBoolean(key, value) }
    }
}
