@file:JvmName("-LiveData")
@file:Suppress("unused")

package net.xpece.androidx.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observeNullable(
    owner: LifecycleOwner,
    crossinline observer: (T?) -> Unit
): Observer<T> {
    val observer1 = Observer<T> { observer(it) }
    observe(owner, observer1)
    return observer1
}

@Deprecated(
    message = "Renamed.",
    replaceWith = ReplaceWith("observeNonNull(owner, observer)", "net.xpece.androidx.lifecycle.observeNonNull")
)
inline fun <T> LiveData<T>.observeFilterNotNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) = observeNonNull(owner, observer)

inline fun <T> LiveData<T>.observeNonNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
): Observer<T> {
    val observer1 = Observer<T> { it?.let(observer) }
    observe(owner, observer1)
    return observer1
}

/**
 * See [https://github.com/hadilq/LiveEvent].
 */
@Deprecated("Consider using LiveEvent library instead.")
@Suppress("NOTHING_TO_INLINE")
@MainThread
inline fun <T> MutableLiveData<T>.setValueAndNull(value: T) {
    setValue(value); setValue(null)
}

/**
 * See [https://github.com/hadilq/LiveEvent].
 */
@Deprecated("Consider using LiveEvent library instead.")
@Suppress("NOTHING_TO_INLINE")
inline fun <T> MutableLiveData<T>.postValueAndNull(value: T) {
    postValue(value); postValue(null)
}

