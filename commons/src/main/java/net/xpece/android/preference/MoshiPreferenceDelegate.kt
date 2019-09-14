package net.xpece.android.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.JsonAdapter
import kotlin.reflect.KProperty

// TODO Implement caching.
class MoshiPreferenceDelegate<T>(val prefs: SharedPreferences, val key: String, val adapter: JsonAdapter<T>, val useCache : Boolean = true) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        val string = prefs.getString(key, null) ?: return null
        return adapter.fromJson(string)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if (value == null) {
            prefs.edit { remove(key) }
        } else {
            val string = adapter.toJson(value)
            prefs.edit { putString(key, string) }
        }
    }
}
