@file:Suppress("FunctionName", "unused")

package io.reactivex

@JvmSynthetic
fun <T> Single(callable: () -> T) = Single.fromCallable(callable)!!

@JvmSynthetic
fun Completable(callable: () -> Unit) = Completable.fromCallable(callable)!!

@JvmSynthetic
fun <T> Observable(callable: () -> T) = Observable.fromCallable(callable)!!

@JvmSynthetic
fun <T> Maybe(callable: () -> T) = Maybe.fromCallable(callable)!!

@JvmSynthetic
fun <T> Flowable(callable: () -> T) = Flowable.fromCallable(callable)!!

@JvmSynthetic
fun <T> Single(source: (SingleEmitter<T>) -> Unit) = Single.create(source)!!

@JvmSynthetic
fun Completable(source: (CompletableEmitter) -> Unit) = Completable.create(source)!!

@JvmSynthetic
fun <T> Observable(source: (ObservableEmitter<T>) -> Unit) = Observable.create(source)!!

@JvmSynthetic
fun <T> Maybe(source: (MaybeEmitter<T>) -> Unit) = Maybe.create(source)!!

@JvmSynthetic
fun <T> Flowable(
        mode: BackpressureStrategy, source: (FlowableEmitter<T>) -> Unit) =
        Flowable.create(source, mode)!!
