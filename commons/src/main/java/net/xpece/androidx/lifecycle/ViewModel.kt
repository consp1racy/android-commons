@file:JvmName("-ViewModel")
@file:Suppress("unused")

package net.xpece.androidx.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

inline fun <reified T : ViewModel> androidx.fragment.app.FragmentActivity.viewModel() =
    ViewModelProviders.of(this)[T::class.java]

inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.viewModel() =
    ViewModelProviders.of(this)[T::class.java]

inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.activityViewModel() =
    ViewModelProviders.of(activity!!)[T::class.java]

inline fun <reified T : ViewModel> androidx.fragment.app.FragmentActivity.viewModel(factory: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, factory)[T::class.java]

inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.viewModel(factory: ViewModelProvider.Factory) =
    ViewModelProviders.of(this, factory)[T::class.java]

inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.activityViewModel(factory: ViewModelProvider.Factory) =
    ViewModelProviders.of(activity!!, factory)[T::class.java]

inline fun <reified T : ViewModel> androidx.fragment.app.FragmentActivity.lazyViewModel(
    crossinline factory: () -> ViewModelProvider.Factory
) = lazy(LazyThreadSafetyMode.NONE) {
    viewModel<T>(factory())
}

inline fun <reified T : ViewModel> androidx.fragment.app.FragmentActivity.lazyViewModel() =
    lazy(LazyThreadSafetyMode.NONE) { viewModel<T>() }

inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.lazyViewModel(
    crossinline factory: () -> ViewModelProvider.Factory
) = lazy(LazyThreadSafetyMode.NONE) {
    viewModel<T>(factory())
}

inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.lazyViewModel() =
    lazy(LazyThreadSafetyMode.NONE) { viewModel<T>() }

inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.lazyActivityViewModel(
    crossinline factory: () -> ViewModelProvider.Factory
) = lazy(LazyThreadSafetyMode.NONE) {
    activityViewModel<T>(factory())
}

inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.lazyActivityViewModel() =
    lazy(LazyThreadSafetyMode.NONE) { activityViewModel<T>() }
