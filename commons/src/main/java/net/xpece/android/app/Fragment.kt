@file:JvmName("XpFragment")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.app

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


@Suppress("DEPRECATION")
inline fun androidx.fragment.app.Fragment.invalidateOptionsMenu() = activity?.supportInvalidateOptionsMenu()

inline fun android.app.Fragment.invalidateOptionsMenu() = activity?.invalidateOptionsMenu()

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

fun android.app.DialogFragment.showAllowingStateLoss(
        fragmentManager: android.app.FragmentManager, tag: String) {
    try {
        show(fragmentManager, tag)
    } catch (ignored: IllegalStateException) {
    }
}

fun android.app.DialogFragment.showAllowingStateLoss(
        fragmentTransaction: android.app.FragmentTransaction, tag: String) {
    try {
        show(fragmentTransaction, tag)
    } catch (ignored: IllegalStateException) {
    }
}

inline fun <T : androidx.fragment.app.Fragment> T.withArguments(argProvider: Bundle.() -> Unit): T {
    arguments = (arguments ?: Bundle()).apply(argProvider)
    return this
}
