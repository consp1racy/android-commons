package net.xpece.android.net

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.support.annotation.IntDef

/**
 * @author Eugen on 21.08.2016.
 */

class ConnectivityReceiver private constructor(context: Context) : ConnectivityReceiverDelegate {
    companion object {
        private val airplaneModeIntentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)

        const val STATE_CONNECTING = 0L
        const val STATE_CONNECTED = 1L
        const val STATE_DISCONNECTED = 3L

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

        fun getInstance(context: Context) = ConnectivityReceiver(context.applicationContext)
    }

    @IntDef(STATE_CONNECTED, STATE_CONNECTING, STATE_DISCONNECTED)
    annotation class State

    private var connectivityManager: ConnectivityManager? = null
        private set

    private val airplaneModeBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (Intent.ACTION_AIRPLANE_MODE_CHANGED == action) {
                onAirplaneModeAction(context)
            }
        }
    }

    var isAirplaneModeEnabled: Boolean = false
        private set

    @State
    var state: Long = STATE_CONNECTED
        private set

    @Suppress("UNUSED")
    val isConnected: Boolean
        get() = state == STATE_CONNECTED

    @Suppress("UNUSED")
    val isConnecting: Boolean
        get() = state == STATE_CONNECTING

    @Suppress("UNUSED")
    val isDisconnected: Boolean
        get() = state == STATE_DISCONNECTED

    private var callback: ConnectivityCallback? = null

    private val context: Context

    private val impl: ConnectivityReceiverImpl

    init {
        this.context = context.applicationContext

        val sdk = Build.VERSION.SDK_INT
        if (sdk >= 23) {
            impl = ConnectivityReceiverMarshmallow(this)
        } else if (sdk >= 21) {
            impl = ConnectivityReceiverLollipop(this)
        } else {
            impl = ConnectivityReceiverBase(this)
        }
    }

    private fun reset() {
        isAirplaneModeEnabled = false
        state = STATE_CONNECTED
    }

    private fun init(context: Context) {
        reset()
        onAirplaneModeAction(context, true)
        onConnectivityAction(context)
    }

    @Suppress("UNUSED")
    fun register(callback: ConnectivityCallback) {
        if (this.callback == callback) {
            return
        } else if (this.callback != null) {
            unregister()
        }

        this.callback = callback
        context.registerReceiver(airplaneModeBroadcastReceiver, airplaneModeIntentFilter)
        impl.onStartListening(context)
        init(context)
    }

    fun register(callback: (connectivity: ConnectivityReceiver) -> (Unit)) = register(ConnectivityCallback { callback(it) })

    @Suppress("UNUSED")
    fun unregister() {
        if (callback != null) {
            try {
                context.unregisterReceiver(airplaneModeBroadcastReceiver)
            } catch (ex: IllegalArgumentException) {
                //
            }
            impl.onStopListening(context)
            callback = null
        } else {
            throw  IllegalStateException("No callback registered.")
        }
    }

    private fun onConnectivityAction(context: Context, forceIfDisconnected: Boolean = false) {
        ensureConnectivityManager(context)
        val ni = connectivityManager!!.activeNetworkInfo
        val state = toSimpleState(ni?.state)
        val shouldForce = (forceIfDisconnected && state == STATE_DISCONNECTED)
        if (state != this.state || shouldForce) {
            this.state = state
            onActiveNetworkChanged()
        }
    }

    private fun onAirplaneModeAction(context: Context, suppressIfEnabled: Boolean = false) {
        val airplaneModeEnabled = context.isAirplaneModeOn
        if (airplaneModeEnabled != this.isAirplaneModeEnabled) {
            this.isAirplaneModeEnabled = airplaneModeEnabled
//            onAirplaneModeChanged()

            if (airplaneModeEnabled && !suppressIfEnabled) {
                if (suppressIfEnabled) {
                    // Call from init will be handled by onConnectivityAction.
                } else {
                    // Once airplane mode is enabled all connection is ceased.
                    this.state = STATE_DISCONNECTED
                    onActiveNetworkChanged()
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

    override fun onChange() {
        onConnectivityAction(context)
    }

    private fun onActiveNetworkChanged() {
        callback!!.onConnectivityChanged(this)
    }

}
