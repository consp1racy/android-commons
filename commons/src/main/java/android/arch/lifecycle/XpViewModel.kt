package android.arch.lifecycle

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

inline fun <reified T : ViewModel> FragmentActivity.viewModel() =
        ViewModelProviders.of(this)[T::class.java]!!

inline fun <reified T : ViewModel> Fragment.viewModel() =
        ViewModelProviders.of(this)[T::class.java]!!

inline fun <reified T : ViewModel> Fragment.activityViewModel() =
        ViewModelProviders.of(activity!!)[T::class.java]!!

inline fun <reified T : ViewModel> FragmentActivity.viewModel(factory: ViewModelProvider.Factory) =
        ViewModelProviders.of(this, factory)[T::class.java]!!

inline fun <reified T : ViewModel> Fragment.viewModel(factory: ViewModelProvider.Factory) =
        ViewModelProviders.of(this, factory)[T::class.java]!!

inline fun <reified T : ViewModel> Fragment.activityViewModel(factory: ViewModelProvider.Factory) =
        ViewModelProviders.of(activity!!, factory)[T::class.java]!!

inline fun <reified T : ViewModel> FragmentActivity.lazyViewModel(
        crossinline factory: () -> ViewModelProvider.Factory) = lazy(LazyThreadSafetyMode.NONE) {
    viewModel<T>(factory())
}

inline fun <reified T : ViewModel> FragmentActivity.lazyViewModel() =
        lazy(LazyThreadSafetyMode.NONE) { viewModel<T>() }

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
        crossinline factory: () -> ViewModelProvider.Factory) = lazy(LazyThreadSafetyMode.NONE) {
    viewModel<T>(factory())
}

inline fun <reified T : ViewModel> Fragment.lazyViewModel() =
        lazy(LazyThreadSafetyMode.NONE) { viewModel<T>() }

inline fun <reified T : ViewModel> Fragment.lazyActivityViewModel(
        crossinline factory: () -> ViewModelProvider.Factory) = lazy(LazyThreadSafetyMode.NONE) {
    activityViewModel<T>(factory())
}

inline fun <reified T : ViewModel> Fragment.lazyActivityViewModel() =
        lazy(LazyThreadSafetyMode.NONE) { activityViewModel<T>() }
