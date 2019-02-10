@file:JvmName("XpCursor")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.database

import android.database.Cursor
import java.util.*
import androidx.core.database.getIntOrNull as getIntOrNullImpl
import androidx.core.database.getLongOrNull as getLongOrNullImpl
import androidx.core.database.getStringOrNull as getStringOrNullImpl

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

@Deprecated(
    "Use AndroidX.",
    ReplaceWith("getLongOrNull", imports = ["androidx.core.database.getLongOrNull"])
)
inline fun Cursor.getLongOrNull(index: Int): Long? = getLongOrNullImpl(index)

@Deprecated(
    "Use AndroidX.",
    ReplaceWith("getIntOrNull", imports = ["androidx.core.database.getIntOrNull"])
)
inline fun Cursor.getIntOrNull(index: Int): Int? = getIntOrNullImpl(index)

@Deprecated(
    "Use AndroidX.",
    ReplaceWith("getStringOrNull", imports = ["androidx.core.database.getStringOrNull"])
)
inline fun Cursor.getStringOrNull(index: Int): String? = getStringOrNullImpl(index)

inline fun Cursor.getLong(index: Int, fallback: Long): Long =
    getLongOrNullImpl(index) ?: fallback

inline fun Cursor.getInt(index: Int, fallback: Int): Int =
    getIntOrNullImpl(index) ?: fallback

inline fun Cursor.getString(index: Int, fallback: String): String =
    getStringOrNullImpl(index) ?: fallback
