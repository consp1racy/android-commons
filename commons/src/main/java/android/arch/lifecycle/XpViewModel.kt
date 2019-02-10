package android.arch.lifecycle

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import net.xpece.androidx.lifecycle.activityViewModel as activityViewModelImpl
import net.xpece.androidx.lifecycle.lazyActivityViewModel as lazyActivityViewModelImpl
import net.xpece.androidx.lifecycle.lazyViewModel as lazyViewModelImpl
import net.xpece.androidx.lifecycle.viewModel as viewModelImpl

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "viewModel",
        imports = ["net.xpece.androidx.lifecycle.viewModel"]
    )
)
inline fun <reified T : ViewModel> FragmentActivity.viewModel() =
    viewModelImpl<T>()

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "viewModel",
        imports = ["net.xpece.androidx.lifecycle.viewModel"]
    )
)
inline fun <reified T : ViewModel> Fragment.viewModel() =
    viewModelImpl<T>()

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "activityViewModel",
        imports = ["net.xpece.androidx.lifecycle.activityViewModel"]
    )
)
inline fun <reified T : ViewModel> Fragment.activityViewModel() =
    activityViewModelImpl<T>()

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "viewModel",
        imports = ["net.xpece.androidx.lifecycle.viewModel"]
    )
)
inline fun <reified T : ViewModel> FragmentActivity.viewModel(factory: ViewModelProvider.Factory) =
    viewModelImpl<T>(factory)

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "viewModel",
        imports = ["net.xpece.androidx.lifecycle.viewModel"]
    )
)
inline fun <reified T : ViewModel> Fragment.viewModel(factory: ViewModelProvider.Factory) =
    viewModelImpl<T>(factory)

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "activityViewModel",
        imports = ["net.xpece.androidx.lifecycle.activityViewModel"]
    )
)
inline fun <reified T : ViewModel> Fragment.activityViewModel(factory: ViewModelProvider.Factory) =
    activityViewModelImpl<T>(factory)

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "lazyViewModel",
        imports = ["net.xpece.androidx.lifecycle.lazyViewModel"]
    )
)
inline fun <reified T : ViewModel> FragmentActivity.lazyViewModel(
    crossinline factory: () -> ViewModelProvider.Factory
) = lazyViewModelImpl<T>(factory)

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "lazyViewModel",
        imports = ["net.xpece.androidx.lifecycle.lazyViewModel"]
    )
)
inline fun <reified T : ViewModel> FragmentActivity.lazyViewModel() =
    lazyViewModelImpl<T>()

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "lazyViewModel",
        imports = ["net.xpece.androidx.lifecycle.lazyViewModel"]
    )
)
inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    crossinline factory: () -> ViewModelProvider.Factory
) = lazyViewModelImpl<T>(factory)

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "lazyViewModel",
        imports = ["net.xpece.androidx.lifecycle.lazyViewModel"]
    )
)
inline fun <reified T : ViewModel> Fragment.lazyViewModel() =
    lazyViewModelImpl<T>()

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "lazyActivityViewModel",
        imports = ["net.xpece.androidx.lifecycle.lazyActivityViewModel"]
    )
)
inline fun <reified T : ViewModel> Fragment.lazyActivityViewModel(
    crossinline factory: () -> ViewModelProvider.Factory
) = lazyActivityViewModelImpl<T>(factory)

@Deprecated(
    "This extension function is not available when using Jetifier.",
    ReplaceWith(
        "lazyActivityViewModel",
        imports = ["net.xpece.androidx.lifecycle.lazyActivityViewModel"]
    )
)
inline fun <reified T : ViewModel> Fragment.lazyActivityViewModel() =
    lazyActivityViewModelImpl<T>()
