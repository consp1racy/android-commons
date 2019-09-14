package net.xpece.android.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.reflect.KProperty

class StringPreferenceDelegate(val prefs: SharedPreferences, val key: String, val default: String? = null) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String? =
            prefs.getString(key, default)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        if (value == null) {
            prefs.edit { remove(key) }
        } else {
            prefs.edit { putString(key, value) }
        }
    }
}
