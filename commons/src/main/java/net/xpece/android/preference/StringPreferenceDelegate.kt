package net.xpece.android.preference

import android.content.SharedPreferences
import net.xpece.android.content.update
import kotlin.reflect.KProperty

class StringPreferenceDelegate(val prefs: SharedPreferences, val key: String, val default: String? = null) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String? {
        return prefs.getString(key, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        if (value == null) {
            prefs.update { remove(key) }
        } else {
            prefs.update { putString(key, value) }
        }
    }
}
