package net.xpece.android.util

import timber.log.Timber

inline fun tv(message: () -> String) {
    if (Timber.treeCount() > 0) Timber.v(message.invoke())
}

inline fun tv(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount() > 0) Timber.v(throwable, message.invoke())
}

inline fun ti(message: () -> String) {
    if (Timber.treeCount() > 0) Timber.i(message.invoke())
}

inline fun ti(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount() > 0) Timber.i(throwable, message.invoke())
}

inline fun td(message: () -> String) {
    if (Timber.treeCount() > 0) Timber.d(message.invoke())
}

inline fun td(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount() > 0) Timber.d(throwable, message.invoke())
}

inline fun tw(message: () -> String) {
    if (Timber.treeCount() > 0) Timber.w(message.invoke())
}

inline fun tw(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount() > 0) Timber.w(throwable, message.invoke())
}

inline fun te(message: () -> String) {
    if (Timber.treeCount() > 0) Timber.e(message.invoke())
}

inline fun te(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount() > 0) Timber.e(throwable, message.invoke())
}

inline fun twtf(message: () -> String) {
    if (Timber.treeCount() > 0) Timber.wtf(message.invoke())
}

inline fun twtf(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount() > 0) Timber.wtf(throwable, message.invoke())
}
