@file:JvmName("XpCursor")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.database

import android.database.Cursor

inline fun Cursor.getDouble(index: Int, fallback: Double): Double =
        if (!isNull(index)) getDouble(index) else fallback

inline fun Cursor.getFloat(index: Int, fallback: Float): Float =
        if (!isNull(index)) getFloat(index) else fallback

inline fun Cursor.getInt(index: Int, fallback: Int): Int =
        if (!isNull(index)) getInt(index) else fallback

inline fun Cursor.getLong(index: Int, fallback: Long): Long =
        if (!isNull(index)) getLong(index) else fallback

inline fun Cursor.getShort(index: Int, fallback: Short): Short =
        if (!isNull(index)) getShort(index) else fallback

inline fun Cursor.getString(index: Int, fallback: String): String =
        if (!isNull(index)) getString(index) else fallback
