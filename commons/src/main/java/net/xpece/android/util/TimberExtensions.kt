@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.util

import timber.log.Timber

@Deprecated("Use TimberKt.", ReplaceWith("v", "com.github.ajalt.timberkt.v"))
inline fun tv(message: () -> String) {
    if (Timber.treeCount() > 0) Timber.v(message.invoke())
}

@Deprecated("Use TimberKt.", ReplaceWith("v", "com.github.ajalt.timberkt.v"))
inline fun tv(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount() > 0) Timber.v(throwable, message.invoke())
}

@Deprecated("Use TimberKt.", ReplaceWith("v", "com.github.ajalt.timberkt.v"))
inline fun tv(throwable: Throwable?) {
    if (Timber.treeCount() > 0) Timber.v(throwable)
}

@Deprecated("Use TimberKt.", ReplaceWith("i", "com.github.ajalt.timberkt.i"))
inline fun ti(message: () -> String) {
    if (Timber.treeCount() > 0) Timber.i(message.invoke())
}

@Deprecated("Use TimberKt.", ReplaceWith("i", "com.github.ajalt.timberkt.i"))
inline fun ti(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount() > 0) Timber.i(throwable, message.invoke())
}

@Deprecated("Use TimberKt.", ReplaceWith("i", "com.github.ajalt.timberkt.i"))
inline fun ti(throwable: Throwable?) {
    if (Timber.treeCount() > 0) Timber.i(throwable)
}

@Deprecated("Use TimberKt.", ReplaceWith("d", "com.github.ajalt.timberkt.d"))
inline fun td(message: () -> String) {
    if (Timber.treeCount() > 0) Timber.d(message.invoke())
}

@Deprecated("Use TimberKt.", ReplaceWith("d", "com.github.ajalt.timberkt.d"))
inline fun td(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount() > 0) Timber.d(throwable, message.invoke())
}

@Deprecated("Use TimberKt.", ReplaceWith("d", "com.github.ajalt.timberkt.d"))
inline fun td(throwable: Throwable?) {
    if (Timber.treeCount() > 0) Timber.d(throwable)
}

@Deprecated("Use TimberKt.", ReplaceWith("w", "com.github.ajalt.timberkt.w"))
inline fun tw(message: () -> String) {
    if (Timber.treeCount() > 0) Timber.w(message.invoke())
}

@Deprecated("Use TimberKt.", ReplaceWith("w", "com.github.ajalt.timberkt.w"))
inline fun tw(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount() > 0) Timber.w(throwable, message.invoke())
}

@Deprecated("Use TimberKt.", ReplaceWith("w", "com.github.ajalt.timberkt.w"))
inline fun tw(throwable: Throwable?) {
    if (Timber.treeCount() > 0) Timber.w(throwable)
}

@Deprecated("Use TimberKt.", ReplaceWith("e", "com.github.ajalt.timberkt.e"))
inline fun te(message: () -> String) {
    if (Timber.treeCount() > 0) Timber.e(message.invoke())
}

@Deprecated("Use TimberKt.", ReplaceWith("e", "com.github.ajalt.timberkt.e"))
inline fun te(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount() > 0) Timber.e(throwable, message.invoke())
}

@Deprecated("Use TimberKt.", ReplaceWith("e", "com.github.ajalt.timberkt.e"))
inline fun te(throwable: Throwable?) {
    if (Timber.treeCount() > 0) Timber.e(throwable)
}

@Deprecated("Use TimberKt.", ReplaceWith("wtf", "com.github.ajalt.timberkt.wtf"))
inline fun twtf(message: () -> String) {
    if (Timber.treeCount() > 0) Timber.wtf(message.invoke())
}

@Deprecated("Use TimberKt.", ReplaceWith("wtf", "com.github.ajalt.timberkt.wtf"))
inline fun twtf(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount() > 0) Timber.wtf(throwable, message.invoke())
}

@Deprecated("Use TimberKt.", ReplaceWith("wtf", "com.github.ajalt.timberkt.wtf"))
inline fun twtf(throwable: Throwable?) {
    if (Timber.treeCount() > 0) Timber.wtf(throwable)
}
