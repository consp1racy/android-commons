package net.xpece.android.net

import android.content.Context


internal interface ConnectivityReceiverImpl {
    fun onStartListening(context: Context)
    fun onStopListening(context: Context)
}
