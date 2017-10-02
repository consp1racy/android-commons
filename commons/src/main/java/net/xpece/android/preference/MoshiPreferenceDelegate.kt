package net.xpece.android.preference

import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import net.xpece.android.content.update
import kotlin.reflect.KProperty

// TODO Implement caching.
class MoshiPreferenceDelegate<T>(val prefs: SharedPreferences, val key: String, val adapter: JsonAdapter<T>, val useCache : Boolean = true) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        val string = prefs.getString(key, null) ?: return null
        return adapter.fromJson(string)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if (value == null) {
            prefs.update { remove(key) }
        } else {
            val string = adapter.toJson(value)
            prefs.update { putString(key, string) }
        }
    }
}
