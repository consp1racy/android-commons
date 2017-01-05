package net.xpece.android.net

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager

/**
 * @author Eugen on 21.08.2016.
 */

open internal class ConnectivityReceiverBase(val delegate: ConnectivityReceiverDelegate) : ConnectivityReceiverImpl {
    companion object {
        private val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == ConnectivityManager.CONNECTIVITY_ACTION) {
                delegate.onConnectivityChanged()
            }
        }
    }

    override fun onStartListening(context: Context) {
        context.applicationContext.registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStopListening(context: Context) {
        try {
            context.applicationContext.unregisterReceiver(broadcastReceiver)
        } catch (ex: IllegalArgumentException) {
            //
        }
    }
}
