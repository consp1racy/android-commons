package android.arch.lifecycle

import android.support.annotation.MainThread

inline fun <T> LiveData<T>.observeNullable(
        owner: LifecycleOwner, crossinline observer: (T?) -> Unit) =
        observe(owner, Observer { observer(it) })

inline fun <T> LiveData<T>.observeFilterNotNull(
        owner: LifecycleOwner, crossinline observer: (T) -> Unit) =
        observe(owner, Observer { it?.let(observer) })

inline fun <T> LiveData<T>.observe(
        owner: LifecycleOwner, crossinline observer: (T) -> Unit, crossinline ifNull: () -> Unit) =
        observe(owner, Observer { it?.let(observer) ?: ifNull() })

@Suppress("NOTHING_TO_INLINE")
@MainThread
inline fun <T> MutableLiveData<T>.setValueAndNull(value: T) {
    setValue(value); setValue(null)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> MutableLiveData<T>.postValueAndNull(value: T) {
    postValue(value); postValue(null)
}

