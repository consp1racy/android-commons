package android.arch.lifecycle

import android.support.annotation.MainThread
import net.xpece.androidx.lifecycle.observe as observeImpl
import net.xpece.androidx.lifecycle.observeFilterNotNull as observeFilterNotNullImpl
import net.xpece.androidx.lifecycle.observeNullable as observeNullableImpl
import net.xpece.androidx.lifecycle.postValueAndNull as postValueAndNullImpl
import net.xpece.androidx.lifecycle.setValueAndNull as setValueAndNullImpl

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "observeNullable",
        imports = ["net.xpece.androidx.lifecycle.observeNullable"]
    )
)
inline fun <T> LiveData<T>.observeNullable(
    owner: LifecycleOwner, crossinline observer: (T?) -> Unit
) = observeNullableImpl(owner, observer)

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "observeFilterNotNull",
        imports = ["net.xpece.androidx.lifecycle.observeFilterNotNull"]
    )
)
inline fun <T> LiveData<T>.observeFilterNotNull(
    owner: LifecycleOwner, crossinline observer: (T) -> Unit
) = observeFilterNotNullImpl(owner, observer)

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "observe",
        imports = ["net.xpece.androidx.lifecycle.observe"]
    )
)
inline fun <T> LiveData<T>.observe(
    owner: LifecycleOwner, crossinline observer: (T) -> Unit, crossinline ifNull: () -> Unit
) = observeImpl(owner, observer, ifNull)

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "setValueAndNull",
        imports = ["net.xpece.androidx.lifecycle.setValueAndNull"]
    )
)
@Suppress("NOTHING_TO_INLINE")
@MainThread
inline fun <T> MutableLiveData<T>.setValueAndNull(value: T) =
    setValueAndNullImpl(value)

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "postValueAndNull",
        imports = ["net.xpece.androidx.lifecycle.postValueAndNull"]
    )
)
@Suppress("NOTHING_TO_INLINE")
inline fun <T> MutableLiveData<T>.postValueAndNull(value: T) =
    postValueAndNullImpl(value)

