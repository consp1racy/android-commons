@file:JvmName("XpPreferences")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.content

import android.content.SharedPreferences
import androidx.core.content.edit

@Deprecated(
    "Use AndroidX.",
    ReplaceWith("edit(commit, func)", imports = ["androidx.core.content.edit"])
)
inline fun SharedPreferences.update(commit: Boolean = false, func: SharedPreferences.Editor.() -> Unit) {
    edit(commit, func)
}

inline operator fun SharedPreferences.Editor.set(key: String, value: String) = putString(key, value)!!
inline operator fun SharedPreferences.Editor.set(key: String, value: Int) = putInt(key, value)!!
inline operator fun SharedPreferences.Editor.set(key: String, value: Long) = putLong(key, value)!!
inline operator fun SharedPreferences.Editor.set(key: String, value: Boolean) = putBoolean(key, value)!!
inline operator fun SharedPreferences.Editor.set(key: String, value: Float) = putFloat(key, value)!!
inline operator fun SharedPreferences.Editor.set(key: String, value: Set<String>) = putStringSet(key, value)!!
