package net.xpece.android.app

import android.support.v4.app.Fragment

/**
 * Created by Eugen on 29.10.2016.
 */

fun Fragment.invalidateOptionsMenu() = activity?.apply { supportInvalidateOptionsMenu() }
fun android.app.Fragment.invalidateOptionsMenu() = activity?.apply { invalidateOptionsMenu() }
