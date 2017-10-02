@file:JvmName("XpPreferences")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.content

import android.annotation.SuppressLint
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits", "ApplySharedPref")
inline fun SharedPreferences.update(commit: Boolean = false, func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    if (commit) {
        editor.commit()
    } else {
        editor.apply()
    }
}

inline operator fun SharedPreferences.Editor.set(key: String, value: String) = putString(key, value)!!
inline operator fun SharedPreferences.Editor.set(key: String, value: Int) = putInt(key, value)!!
inline operator fun SharedPreferences.Editor.set(key: String, value: Long) = putLong(key, value)!!
inline operator fun SharedPreferences.Editor.set(key: String, value: Boolean) = putBoolean(key, value)!!
inline operator fun SharedPreferences.Editor.set(key: String, value: Float) = putFloat(key, value)!!
inline operator fun SharedPreferences.Editor.set(key: String, value: Set<String>) = putStringSet(key, value)!!

@Deprecated("")
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
