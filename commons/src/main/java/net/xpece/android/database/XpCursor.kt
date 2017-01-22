@file:JvmName("XpCursor")

package net.xpece.android.database

import android.database.Cursor
import java.util.*

/**
 * @author Eugen on 24. 6. 2016.
 */
private val cursorMap: WeakHashMap<Cursor, MutableMap<String, Int>> = WeakHashMap()

fun Cursor.getColumnIndexCached(columnName: String): Int {
    var columnMap = cursorMap[this]
    if (columnMap == null) {
        columnMap = mutableMapOf()
        cursorMap[this] = columnMap
    }
    var columnIndex = columnMap[columnName]
    if (columnIndex == null) {
        columnIndex = getColumnIndex(columnName)
        columnMap[columnName] = columnIndex
    }
    return columnIndex
}

fun Cursor.getLongOrNull(index: Int): Long? {
    if (isNull(index)) {
        return null
    } else {
        return getLong(index)
    }
}

fun Cursor.getIntOrNull(index: Int): Int? {
    if (isNull(index)) {
        return null
    } else {
        return getInt(index)
    }
}

fun Cursor.getStringOrNull(index: Int): String? {
    if (isNull(index)) {
        return null
    } else {
        return getString(index)
    }
}

fun Cursor.getLong(index: Int, fallback: Long): Long {
    if (isNull(index)) {
        return fallback
    } else {
        return getLong(index)
    }
}

fun Cursor.getInt(index: Int, fallback: Int): Int {
    if (isNull(index)) {
        return fallback
    } else {
        return getInt(index)
    }
}

fun Cursor.getString(index: Int, fallback: String): String {
    if (isNull(index)) {
        return fallback
    } else {
        return getString(index)
    }
}
