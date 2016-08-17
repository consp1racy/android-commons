@file:JvmName("XpDatabase")

package net.xpece.android.database

import android.database.Cursor

/**
 * @author Eugen on 29.07.2016.
 */

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

fun SelectionBuilder.where(selection: String?, selectionArgs: Array<out String>?): SelectionBuilder {
    if (selectionArgs == null) {
        this.where(selection)
    } else {
        this.where(selection, *selectionArgs)
    }
    return this
}

inline fun io.requery.android.database.sqlite.SQLiteDatabase.withTransaction(func: io.requery.android.database.sqlite.SQLiteDatabase.() -> Unit) {
    beginTransaction()
    try {
        func()
        setTransactionSuccessful()
    } finally {
        endTransaction()
    }
}


inline fun android.database.sqlite.SQLiteDatabase.withTransaction(func: android.database.sqlite.SQLiteDatabase.() -> Unit) {
    beginTransaction()
    try {
        func()
        setTransactionSuccessful()
    } finally {
        endTransaction()
    }
}
