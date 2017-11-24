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
        @JvmStatic
        private val airplaneModeIntentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)

        const val STATE_CONNECTING = 0L
        const val STATE_CONNECTED = 1L
        const val STATE_DISCONNECTED = 3L

        @State
        @JvmStatic
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

        @Suppress("UNUSED")
        @JvmStatic
        fun newInstance(context: Context) = ConnectivityReceiver(context.applicationContext)
    }

    @IntDef(STATE_CONNECTED, STATE_CONNECTING, STATE_DISCONNECTED)
    annotation class State

    private var connectivityManager: ConnectivityManager? = null
        private set

    private val airplaneModeBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (Intent.ACTION_AIRPLANE_MODE_CHANGED == action) {
                onAirplaneModeChanged()
            }
        }
    }

    var info = ConnectivityInfo.DEFAULT

    private val callbacks: MutableList<ConnectivityCallback> = mutableListOf()

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
        info = ConnectivityInfo.DEFAULT
    }

    private fun init() {
        reset()
        onAirplaneModeChanged(true)
        onConnectivityChanged()
    }

    @Suppress("UNUSED")
    fun register(callback: ConnectivityCallback) {
        if (callbacks.contains(callback)) {
            return
        }

        callbacks.add(callback)

        if (callbacks.size == 1) {
            // First callback added, start listening.
            startListening()
        }
    }

    @Suppress("UNUSED")
    fun register(callback: (connectivity: ConnectivityInfo) -> Unit) =
            register(ConnectivityCallback { callback(it) })

    /**
     * Unregister all [ConnectivityCallback]s and stop listening for connectivity changes.
     */
    @Suppress("UNUSED")
    fun unregister() {
        val count = callbacks.size
        if (count > 0) {
            callbacks.clear()
            stopListening()
        }
    }

    @Suppress("UNUSED")
    fun unregister(callback: ConnectivityCallback) {
        if (callbacks.contains(callback)) {
            callbacks.remove(callback)
            if (callbacks.size == 0) {
                // Removed last callback, stop listening.
                stopListening()
            }
        } else {
            throw IllegalArgumentException("Cannot unregister callback $callback, was not registered.")
        }
    }

    private fun startListening() {
        context.registerReceiver(airplaneModeBroadcastReceiver, airplaneModeIntentFilter)
        impl.onStartListening(context)
        init()
    }

    private fun stopListening() {
        try {
            context.unregisterReceiver(airplaneModeBroadcastReceiver)
        } catch (ex: IllegalArgumentException) {
            //
        }
        impl.onStopListening(context)
    }

    override fun onConnectivityChanged() {
        onConnectivityChanged(false)
    }

    private fun onConnectivityChanged(forceIfDisconnected: Boolean) {
        ensureConnectivityManager(context)
        val ni = connectivityManager!!.activeNetworkInfo
        val state = toSimpleState(ni?.state)
        val shouldForce = (forceIfDisconnected && state == STATE_DISCONNECTED)
        if (state != info.state || shouldForce) {
            info = info.copy(state = state)
            onActiveNetworkChanged()
        }
    }

    private fun onAirplaneModeChanged(suppressIfEnabled: Boolean = false) {
        val airplaneModeEnabled = context.isAirplaneModeOn
        if (airplaneModeEnabled != info.isAirplaneModeEnabled) {
            info = info.copy(isAirplaneModeEnabled = airplaneModeEnabled)
//            onAirplaneModeChanged()

            if (airplaneModeEnabled && !suppressIfEnabled) {
                if (suppressIfEnabled) {
                    // Call from init will be handled by onConnectivityAction.
                } else {
                    // Once airplane mode is enabled all connection is ceased.
                    info = info.copy(state = STATE_DISCONNECTED)
                    onActiveNetworkChanged()
                }
            } else {
                // Check current active network.
                onConnectivityChanged(true)
            }
        }
    }

    private fun ensureConnectivityManager(context: Context) {
        if (connectivityManager == null) {
            connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
    }

    private fun onActiveNetworkChanged() {
        callbacks.forEach { it.onConnectivityChanged(info) }
    }

}
