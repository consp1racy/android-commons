package net.xpece.android.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.reflect.KProperty

abstract class PrintablePreferenceDelegate<T>(val prefs: SharedPreferences, val key: String, val default: T? = null) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        val string = prefs.getString(key, default.toString())!!
        return fromString(string)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if (value == null) {
            prefs.edit { remove(key) }
        } else {
            val odt = toString(value)
            prefs.edit { putString(key, odt) }
        }
    }

    open protected fun toString(input: T) = input.toString()

    abstract protected fun fromString(input: String) : T
}
