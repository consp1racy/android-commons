@file:JvmName("XpDatabase")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.database

import androidx.core.database.sqlite.transaction

fun SelectionBuilder.where(
    selection: String?,
    selectionArgs: Array<out String>?
): SelectionBuilder {
    if (selectionArgs == null) {
        this.where(selection)
    } else {
        this.where(selection, *selectionArgs)
    }
    return this
}

@Deprecated("Use Requery that extends SupportSQLiteDatabase and AndroidX extensions.")
inline fun io.requery.android.database.sqlite.SQLiteDatabase.withTransaction(func: io.requery.android.database.sqlite.SQLiteDatabase.() -> Unit) {
    beginTransaction()
    try {
        func()
        setTransactionSuccessful()
    } finally {
        endTransaction()
    }
}

@Deprecated(
    "Use AndroidX.",
    ReplaceWith("transaction", imports = ["androidx.core.database.sqlite.transaction"])
)
inline fun android.database.sqlite.SQLiteDatabase.withTransaction(noinline func: android.database.sqlite.SQLiteDatabase.() -> Unit) {
    transaction(true, func)
}
