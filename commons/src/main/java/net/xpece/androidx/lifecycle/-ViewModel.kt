@file:JvmName("-ViewModel")
@file:Suppress("unused")

package net.xpece.androidx.lifecycle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> FragmentActivity.viewModel() =
    ViewModelProviders.of(this)[T::class.java]

inline fun <reified T : ViewModel> Fragment.viewModel() =
    ViewModelProviders.of(this)[T::class.java]

inline fun <reified T : ViewModel> Fragment.activityViewModel() =
    ViewModelProviders.of(activity!!)[T::class.java]

inline fun <reified T : ViewModel> FragmentActivity.viewModel(factory: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, factory)[T::class.java]

inline fun <reified T : ViewModel> Fragment.viewModel(factory: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, factory)[T::class.java]

inline fun <reified T : ViewModel> Fragment.activityViewModel(factory: ViewModelProvider.Factory) =
    ViewModelProviders.of(activity!!, factory)[T::class.java]

inline fun <reified T : ViewModel> FragmentActivity.lazyViewModel(
    crossinline factory: () -> ViewModelProvider.Factory
) = lazy(LazyThreadSafetyMode.NONE) {
    viewModel<T>(factory())
}

inline fun <reified T : ViewModel> FragmentActivity.lazyViewModel() =
    lazy(LazyThreadSafetyMode.NONE) { viewModel<T>() }

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    crossinline factory: () -> ViewModelProvider.Factory
) = lazy(LazyThreadSafetyMode.NONE) {
    viewModel<T>(factory())
}

inline fun <reified T : ViewModel> Fragment.lazyViewModel() =
    lazy(LazyThreadSafetyMode.NONE) { viewModel<T>() }

inline fun <reified T : ViewModel> Fragment.lazyActivityViewModel(
    crossinline factory: () -> ViewModelProvider.Factory
) = lazy(LazyThreadSafetyMode.NONE) {
    activityViewModel<T>(factory())
}

inline fun <reified T : ViewModel> Fragment.lazyActivityViewModel() =
    lazy(LazyThreadSafetyMode.NONE) { activityViewModel<T>() }
