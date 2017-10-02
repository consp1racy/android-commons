@file:JvmName("XpFragment")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.app

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.app.updateLoaderManagerHostController as updateLoaderManagerHostControllerImpl

/**
 * Created by Eugen on 29.10.2016.
 */

@Suppress("DEPRECATION")
inline fun Fragment.invalidateOptionsMenu() = activity.supportInvalidateOptionsMenu()

inline fun android.app.Fragment.invalidateOptionsMenu() = activity.invalidateOptionsMenu()

inline fun DialogFragment.showAllowingStateLoss(fragmentManager: FragmentManager, tag: String) {
    try {
        show(fragmentManager, tag)
    } catch (ignored: IllegalStateException) {
    }
}

inline fun DialogFragment.showAllowingStateLoss(
        fragmentTransaction: FragmentTransaction, tag: String) {
    try {
        show(fragmentTransaction, tag)
    } catch (ignored: IllegalStateException) {
    }
}

inline fun android.app.DialogFragment.showAllowingStateLoss(
        fragmentManager: android.app.FragmentManager, tag: String) {
    try {
        show(fragmentManager, tag)
    } catch (ignored: IllegalStateException) {
    }
}

inline fun android.app.DialogFragment.showAllowingStateLoss(
        fragmentTransaction: android.app.FragmentTransaction, tag: String) {
    try {
        show(fragmentTransaction, tag)
    } catch (ignored: IllegalStateException) {
    }
}

inline fun <T : Fragment> T.withArguments(argProvider: Bundle.() -> Unit): T {
    arguments = (arguments ?: Bundle()).apply(argProvider)
    return this
}
