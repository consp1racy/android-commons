package net.xpece.android.net

import android.content.Context

/**
 * @author Eugen on 04.01.2017.
 */

internal interface ConnectivityReceiverImpl {
    fun onStartListening(context: Context)
    fun onStopListening(context: Context)
}
