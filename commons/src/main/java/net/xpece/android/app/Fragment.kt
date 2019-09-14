@file:JvmName("XpFragment")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.app

import android.os.Bundle


@Suppress("DEPRECATION")
inline fun androidx.fragment.app.Fragment.invalidateOptionsMenu() = activity?.supportInvalidateOptionsMenu()

fun androidx.fragment.app.DialogFragment.showAllowingStateLoss(fragmentManager: androidx.fragment.app.FragmentManager, tag: String) {
    try {
        show(fragmentManager, tag)
    } catch (ignored: IllegalStateException) {
    }
}

fun androidx.fragment.app.DialogFragment.showAllowingStateLoss(
        fragmentTransaction: androidx.fragment.app.FragmentTransaction, tag: String) {
    try {
        show(fragmentTransaction, tag)
    } catch (ignored: IllegalStateException) {
    }
}

inline fun <T : androidx.fragment.app.Fragment> T.withArguments(argProvider: Bundle.() -> Unit): T {
    arguments = (arguments ?: Bundle()).apply(argProvider)
    return this
}
