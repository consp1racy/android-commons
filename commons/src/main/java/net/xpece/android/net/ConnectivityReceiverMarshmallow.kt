package net.xpece.android.net

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.PowerManager
import android.support.annotation.RequiresApi

/**
 * @author Eugen on 04.01.2017.
 */

@RequiresApi(23)
@TargetApi(23)
open internal class ConnectivityReceiverMarshmallow(delegate: ConnectivityReceiverDelegate) : ConnectivityReceiverLollipop(delegate) {

    private val idleIntentFilter = IntentFilter(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED)

    private val idleBroadcastReceiver =
            object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    delegate.onConnectivityChanged()
                }
            }

    override fun onStartListening(context: Context) {
        super.onStartListening(context)

        context.applicationContext.registerReceiver(idleBroadcastReceiver, idleIntentFilter)
    }

    override fun onStopListening(context: Context) {
        super.onStopListening(context)

        try {
            context.applicationContext.unregisterReceiver(idleBroadcastReceiver)
        } catch (ex: IllegalArgumentException) {
            //
        }
    }

}
