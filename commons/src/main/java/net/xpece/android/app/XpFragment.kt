@file:JvmName("XpFragment")

package net.xpece.android.app

import android.support.v4.app.Fragment
import android.support.v4.app.updateLoaderManagerHostController

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
