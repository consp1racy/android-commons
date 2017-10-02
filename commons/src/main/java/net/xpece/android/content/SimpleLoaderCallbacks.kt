package net.xpece.android.content

import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader

/**
 * @author Eugen on 04.01.2017.
 */
@Deprecated("Don't use loaders.")
abstract class SimpleLoaderCallbacks<D> : LoaderManager.LoaderCallbacks<D> {
    override fun onLoaderReset(loader: Loader<D>) {
        //
    }

    abstract override fun onCreateLoader(id: Int, args: Bundle?): Loader<D>

    final override fun onLoadFinished(loader: Loader<D>, data: D?) {
        if (data != null) {
            onLoadSuccess(loader, data)
        } else {
            onLoadError(loader)
        }
    }

    abstract fun onLoadSuccess(loader: Loader<D>, data: D) : Unit

    open fun onLoadError(loader: Loader<D>) : Unit {}
}
