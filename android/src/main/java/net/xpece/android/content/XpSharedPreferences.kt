@file:JvmName("XpPreferences")

package net.xpece.android.content

import android.content.SharedPreferences

inline fun SharedPreferences.update(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}

fun SharedPreferences.Editor.put(pair: Pair<String, Any?>) {
    val key = pair.first
    val value = pair.second
    when (value) {
        null -> remove(key)
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Boolean -> putBoolean(key, value)
        is Float -> putFloat(key, value)
        is Long -> putLong(key, value)
        else -> throw IllegalArgumentException()
    }
}
