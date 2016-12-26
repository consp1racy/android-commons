package android.support.v4.app

import android.support.annotation.RestrictTo

/**
 * Hack to force update the LoaderManager's host to avoid a memory leak in retained/detached fragments.
 * Call this in Fragment.onAttach()
 *
 * https://code.google.com/p/android/issues/detail?id=227136
 *
 * Remove when the bug is fixed in support-v4.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
fun Fragment.updateLoaderManagerHostController() {
    mHost?.getLoaderManager(mWho, mLoadersStarted, false)
}
