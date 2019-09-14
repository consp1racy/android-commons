@file:JvmName("-LiveData")
@file:Suppress("unused")

package net.xpece.androidx.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observeNullable(
    owner: LifecycleOwner, crossinline observer: (T?) -> Unit
) = observe(owner, Observer { observer(it) })

inline fun <T> LiveData<T>.observeFilterNotNull(
    owner: LifecycleOwner, crossinline observer: (T) -> Unit
) = observe(owner, Observer { it?.let(observer) })

inline fun <T> LiveData<T>.observe(
    owner: LifecycleOwner, crossinline observer: (T) -> Unit, crossinline ifNull: () -> Unit
) = observe(owner, Observer { it?.let(observer) ?: ifNull() })

@Suppress("NOTHING_TO_INLINE")
@MainThread
inline fun <T> MutableLiveData<T>.setValueAndNull(value: T) {
    setValue(value); setValue(null)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> MutableLiveData<T>.postValueAndNull(value: T) {
    postValue(value); postValue(null)
}

