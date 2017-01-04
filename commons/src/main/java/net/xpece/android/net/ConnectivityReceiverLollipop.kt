package net.xpece.android.net

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.support.annotation.RequiresApi
import net.xpece.android.content.connectivityManager

/**
 * @author Eugen on 04.01.2017.
 */

@RequiresApi(21)
@TargetApi(21)
open internal class ConnectivityReceiverLollipop(val delegate: ConnectivityReceiverDelegate) : ConnectivityReceiverImpl {
    companion object {
        private val networkRequest = NetworkRequest.Builder().build()
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network?) {
            delegate.onChange()
        }

        override fun onAvailable(network: Network?) {
            delegate.onChange()
        }
    }

    override fun onStartListening(context: Context) {
        context.applicationContext.connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onStopListening(context: Context) {
        try {
            context.applicationContext.connectivityManager.unregisterNetworkCallback(networkCallback)
        } catch (ex: IllegalArgumentException) {
            //
        }
    }

}
