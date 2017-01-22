@file:JvmName("XpDatabase")

package net.xpece.android.database

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
