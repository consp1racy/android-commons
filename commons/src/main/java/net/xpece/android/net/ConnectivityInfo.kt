package net.xpece.android.net

import net.xpece.android.net.ConnectivityReceiver

data class ConnectivityInfo internal constructor(@ConnectivityReceiver.State val state: Long, val isAirplaneModeEnabled: Boolean) {
    companion object {
        @JvmField val DEFAULT = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_CONNECTED, false)
    }

    val isConnected: Boolean
        get() = state == ConnectivityReceiver.STATE_CONNECTED

    val isConnecting: Boolean
        get() = state == ConnectivityReceiver.STATE_CONNECTING

    val isDisconnected: Boolean
        get() = state == ConnectivityReceiver.STATE_DISCONNECTED
}
