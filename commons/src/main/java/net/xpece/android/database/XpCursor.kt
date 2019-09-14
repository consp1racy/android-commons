@file:JvmName("XpCursor")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.database

import android.database.Cursor
import androidx.core.database.getIntOrNull
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull

inline fun Cursor.getLong(index: Int, fallback: Long): Long =
    getLongOrNull(index) ?: fallback

inline fun Cursor.getInt(index: Int, fallback: Int): Int =
    getIntOrNull(index) ?: fallback

inline fun Cursor.getString(index: Int, fallback: String): String =
    getStringOrNull(index) ?: fallback
