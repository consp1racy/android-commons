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
