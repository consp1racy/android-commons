package net.xpece.android.preference

import android.content.SharedPreferences
import net.xpece.android.content.update
import kotlin.reflect.KProperty

class IntPreferenceDelegate(val prefs: SharedPreferences, val key: String, val default: Int = 0) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return prefs.getInt(key, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        prefs.update { putInt(key, value) }
    }
}
