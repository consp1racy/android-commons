@file:JvmName("XpFragment")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.app

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


@Suppress("DEPRECATION")
inline fun Fragment.invalidateOptionsMenu() = activity?.supportInvalidateOptionsMenu()

fun DialogFragment.showAllowingStateLoss(fragmentManager: FragmentManager, tag: String) {
    try {
        show(fragmentManager, tag)
    } catch (ignored: IllegalStateException) {
    }
}

fun DialogFragment.showAllowingStateLoss(fragmentTransaction: FragmentTransaction, tag: String) {
    try {
        show(fragmentTransaction, tag)
    } catch (ignored: IllegalStateException) {
    }
}

inline fun <T : Fragment> T.withArguments(argProvider: Bundle.() -> Unit): T = apply {
    arguments = (arguments ?: Bundle()).apply(argProvider)
}
