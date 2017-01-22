package net.xpece.android.preference

import android.content.SharedPreferences
import net.xpece.android.content.update
import kotlin.reflect.KProperty

class LongPreferenceDelegate(val prefs: SharedPreferences, val key: String, val default: Long = 0L) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Long {
        return prefs.getLong(key, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
        prefs.update { putLong(key, value) }
    }
}
