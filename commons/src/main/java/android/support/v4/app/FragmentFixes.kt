package android.support.v4.app

/**
 * Hack to force update the LoaderManager's host to avoid a memory leak in retained/detached fragments.
 * Call this in Fragment.onAttach()
 *
 * https://code.google.com/p/android/issues/detail?id=227136
 *
 * ~Remove when the bug is fixed in support-v4.~ This has been fixed in v25.3.0.
 */
@Deprecated("This has been fixed in support library 25.3.0.", ReplaceWith(""))
fun Fragment.updateLoaderManagerHostController() {
    mHost?.getLoaderManager(mWho, mLoadersStarted, false)
}
