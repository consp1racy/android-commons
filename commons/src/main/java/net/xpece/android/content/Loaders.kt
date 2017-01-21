package net.xpece.android.content

import android.os.Bundle
import android.support.v4.content.Loader

/**
 * @author Eugen on 04.01.2017.
 */

fun <D> buildLoaderCallbacks(onCreate: () -> Loader<D>,
                             onSuccess: (loader: Loader<D>, data: D) -> (Unit)) = object : SimpleLoaderCallbacks<D>() {
    override fun onCreateLoader(id: Int, args: Bundle?) = onCreate()
    override fun onLoadSuccess(loader: Loader<D>, data: D) = onSuccess(loader, data)
}

fun <D> buildLoaderCallbacks(onCreate: () -> Loader<D>,
                             onSuccess: (loader: Loader<D>, data: D) -> (Unit),
                             onError: (loader: Loader<D>) -> (Unit) = {}) = object : SimpleLoaderCallbacks<D>() {
    override fun onCreateLoader(id: Int, args: Bundle?) = onCreate()
    override fun onLoadSuccess(loader: Loader<D>, data: D) = onSuccess(loader, data)
    override fun onLoadError(loader: Loader<D>) = onError(loader)
}
