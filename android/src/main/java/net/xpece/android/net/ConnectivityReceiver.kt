package net.xpece.android.net

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.annotation.IntDef
import net.xpece.android.util.td

/**
 * @author Eugen on 21.08.2016.
 */

open class ConnectivityReceiver : BroadcastReceiver() {

    protected var connectivityManager: ConnectivityManager? = null
        private set

    var airplaneModeEnabled: Boolean = false
        private set

    @State
    var state: Long = STATE_CONNECTED
        private set

    val isConnected: Boolean
        get() = state == STATE_CONNECTED

    private fun reset() {
        airplaneModeEnabled = false
        state = STATE_CONNECTED
    }

    fun init(context: Context) {
        reset()
        onAirplaneModeAction(context, true)
        onConnectivityAction(context)
    }

    fun register(context: Context) {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(this, filter)
    }

    fun unregister(context: Context) {
        try {
            context.unregisterReceiver(this)
        } catch (ex: IllegalArgumentException) {
            //
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED == action) {
            onAirplaneModeAction(context)
        } else if (ConnectivityManager.CONNECTIVITY_ACTION == action) {
            onConnectivityAction(context)
        }
    }

    private fun onConnectivityAction(context: Context, forceIfDisconnected: Boolean = false) {
        ensureConnectivityManager(context)
        val ni = connectivityManager!!.activeNetworkInfo
        val state = toSimpleState(ni?.state)
        val shouldForce = (forceIfDisconnected && state == STATE_DISCONNECTED)
        if (state != this.state || shouldForce) {
            td { "onActiveNetworkChanged: $state, $ni" }
            this.state = state
            onActiveNetworkChanged()
        }
    }

    private fun onAirplaneModeAction(context: Context, suppressIfEnabled: Boolean = false) {
        val airplaneModeEnabled = context.isAirplaneModeOn()
        if (airplaneModeEnabled != this.airplaneModeEnabled) {
            td { "onAirplaneModeChanged: $airplaneModeEnabled" }
            this.airplaneModeEnabled = airplaneModeEnabled
//            onAirplaneModeChanged()

            if (airplaneModeEnabled && !suppressIfEnabled) {
                if (suppressIfEnabled) {
                    // Call from init will be handled by onConnectivityAction.
                } else {
                    // Once airplane mode is enabled all connection is ceased.
                    this.state = STATE_DISCONNECTED
                    onDisconnected()
                }
            } else {
                // Check current active network.
                onConnectivityAction(context, true)
            }
        }
    }

    private fun ensureConnectivityManager(context: Context) {
        if (connectivityManager == null) {
            connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
    }

    fun onActiveNetworkChanged() {
        when (state) {
            STATE_CONNECTED -> onConnected()
            STATE_CONNECTING -> onConnecting()
            STATE_DISCONNECTED -> onDisconnected()
        }
    }

    open fun onConnected() {
    }

    open fun onConnecting() {
    }

    /**
     * Check [airplaneModeEnabled] and react accordingly.
     */
    open fun onDisconnected() {
    }

    @State
    private fun toSimpleState(state: NetworkInfo.State?): Long {
        if (state == null) {
            return STATE_DISCONNECTED
        }
        return when (state) {
            NetworkInfo.State.CONNECTED -> STATE_CONNECTED
            NetworkInfo.State.CONNECTING -> STATE_CONNECTING
            else -> STATE_DISCONNECTED
        }
    }

    @IntDef(STATE_CONNECTED, STATE_CONNECTING, STATE_DISCONNECTED)
    annotation class State

    companion object {
        @State
        const val STATE_CONNECTING = 0L
        @State
        const val STATE_CONNECTED = 1L
        @State
        const val STATE_DISCONNECTED = 3L
    }
}
