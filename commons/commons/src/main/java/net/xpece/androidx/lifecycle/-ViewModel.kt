@file:JvmName("-ViewModel")
@file:Suppress("unused")

package net.xpece.androidx.lifecycle

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Deprecated(
    message = "Use AndroidX equivalent.",
    replaceWith = ReplaceWith("viewModels(factory)", "androidx.activity.viewModels"),
    level = DeprecationLevel.ERROR
)
inline fun <reified T : ViewModel> FragmentActivity.lazyViewModel(
    noinline factory: () -> ViewModelProvider.Factory
): Lazy<T> = viewModels(factory)

@Deprecated(
    message = "Use AndroidX equivalent.",
    replaceWith = ReplaceWith("viewModels()", "androidx.activity.viewModels"),
    level = DeprecationLevel.ERROR
)
inline fun <reified T : ViewModel> FragmentActivity.lazyViewModel(): Lazy<T> =
    viewModels()

@Deprecated(
    message = "Use AndroidX equivalent.",
    replaceWith = ReplaceWith("viewModels()", "androidx.fragment.app.viewModels"),
    level = DeprecationLevel.ERROR
)
inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    noinline factory: () -> ViewModelProvider.Factory
): Lazy<T> = viewModels({ this }, factory)

@Deprecated(
    message = "Use AndroidX equivalent.",
    replaceWith = ReplaceWith("viewModels()", "androidx.fragment.app.viewModels"),
    level = DeprecationLevel.ERROR
)
inline fun <reified T : ViewModel> Fragment.lazyViewModel(): Lazy<T> =
    viewModels({ this })

@Deprecated(
    message = "Use AndroidX equivalent.",
    replaceWith = ReplaceWith("activityViewModels()", "androidx.fragment.app.activityViewModels"),
    level = DeprecationLevel.ERROR
)
inline fun <reified T : ViewModel> Fragment.lazyActivityViewModel(
    noinline factory: () -> ViewModelProvider.Factory
): Lazy<T> = activityViewModels(factory)

@Deprecated(
    message = "Use AndroidX equivalent.",
    replaceWith = ReplaceWith("activityViewModels()", "androidx.fragment.app.activityViewModels"),
    level = DeprecationLevel.ERROR
)
inline fun <reified T : ViewModel> Fragment.lazyActivityViewModel(): Lazy<T> =
    activityViewModels()
