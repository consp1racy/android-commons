package net.xpece.android.preference

import android.content.SharedPreferences
import net.xpece.android.content.update
import kotlin.reflect.KProperty

class FloatPreferenceDelegate(val prefs: SharedPreferences, val key: String, val default: Float = 0F) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Float {
        return prefs.getFloat(key, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Float) {
        prefs.update { putFloat(key, value) }
    }
}
