@file:JvmName("XpFragment")

package net.xpece.android.app

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

/**
 * Created by Eugen on 29.10.2016.
 */

fun Fragment.invalidateOptionsMenu() = activity?.apply { supportInvalidateOptionsMenu() }

fun android.app.Fragment.invalidateOptionsMenu() = activity?.apply { invalidateOptionsMenu() }

/**
 * Hack to force update the LoaderManager's host to avoid a memory leak in retained/detached fragments.
 * Call this in Fragment.onAttach()
 *
 * https://code.google.com/p/android/issues/detail?id=227136
 *
 * Remove when the bug is fixed in support-v4.
 */
fun Fragment.updateLoaderManagerHostController() {
    updateLoaderManagerHostController()
}

fun DialogFragment.showAllowingStateLoss(fragmentManager: FragmentManager, tag: String) {
    try {
        show(fragmentManager, tag)
    } catch (ex :IllegalStateException) {
        //
    }
}
fun DialogFragment.showAllowingStateLoss(fragmentTransaction: FragmentTransaction, tag: String) {
    try {
        show(fragmentTransaction, tag)
    } catch (ex :IllegalStateException) {
        //
    }
}

fun android.app.DialogFragment.showAllowingStateLoss(fragmentManager: android.app.FragmentManager, tag: String) {
    try {
        show(fragmentManager, tag)
    } catch (ex :IllegalStateException) {
        //
    }
}
fun android.app.DialogFragment.showAllowingStateLoss(fragmentTransaction: android.app.FragmentTransaction, tag: String) {
    try {
        show(fragmentTransaction, tag)
    } catch (ex :IllegalStateException) {
        //
    }
}

inline fun <T : Fragment> T.withArguments(argProvider: Bundle.() -> Unit): T {
    arguments = (arguments ?:Bundle()).apply(argProvider)
    return this
}
