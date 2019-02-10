@file:Suppress("FunctionName", "unused", "NOTHING_TO_INLINE")
@file:JvmName("-XpReactiveExtensions")

package io.reactivex

inline fun <T> Single(noinline callable: () -> T): Single<T> = Single.fromCallable(callable)

inline fun Completable(noinline action: () -> Unit): Completable = Completable.fromAction(action)

inline fun <T> Observable(noinline callable: () -> T): Observable<T> =
    Observable.fromCallable(callable)

inline fun <T> Maybe(noinline callable: () -> T): Maybe<T> = Maybe.fromCallable(callable)

inline fun <T> Flowable(noinline callable: () -> T): Flowable<T> =
    Flowable.fromCallable(callable)
