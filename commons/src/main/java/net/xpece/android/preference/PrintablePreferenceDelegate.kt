package net.xpece.android.preference

import android.content.SharedPreferences
import net.xpece.android.content.update
import kotlin.reflect.KProperty

abstract class PrintablePreferenceDelegate<T>(val prefs: SharedPreferences, val key: String, val default: T? = null) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        val string = prefs.getString(key, default.toString())
        return fromString(string)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if (value == null) {
            prefs.update { remove(key) }
        } else {
            val odt = toString(value)
            prefs.update { putString(key, odt) }
        }
    }

    open protected fun toString(input: T) = input.toString()

    abstract protected fun fromString(input: String) : T
}
