@file:JvmName("XpPreferences")

package net.xpece.android.content

import android.content.SharedPreferences

inline fun SharedPreferences.update(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}

fun SharedPreferences.Editor.put(pair: Pair<String, Any>) {
    when (pair.second) {
        is String -> putString(pair.first, pair.second as String)
        is Int -> putInt(pair.first, pair.second as Int)
        is Boolean -> putBoolean(pair.first, pair.second as Boolean)
        is Float -> putFloat(pair.first, pair.second as Float)
        is Long -> putLong(pair.first, pair.second as Long)
        else -> throw IllegalArgumentException()
    }
}
