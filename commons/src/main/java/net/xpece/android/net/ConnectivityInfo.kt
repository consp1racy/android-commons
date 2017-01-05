package net.xpece.android.net

class ConnectivityInfo(@ConnectivityReceiver.State val state: Long, val isAirplaneModeEnabled: Boolean) {

    companion object {
        private val CONNECTED = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_CONNECTED, false)
        private val CONNECTING = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_CONNECTING, false)
        private val DISCONNECTED = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_DISCONNECTED, false)
        private val CONNECTED_AIRPLANE = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_CONNECTED, true)
        private val CONNECTING_AIRPLANE = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_CONNECTING, true)
        private val DISCONNECTED_AIRPLANE = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_DISCONNECTED, true)
        @JvmField val DEFAULT = CONNECTED
    }

    val isConnected: Boolean
        get() = state == ConnectivityReceiver.STATE_CONNECTED

    val isConnecting: Boolean
        get() = state == ConnectivityReceiver.STATE_CONNECTING

    val isDisconnected: Boolean
        get() = state == ConnectivityReceiver.STATE_DISCONNECTED

    fun copy(@ConnectivityReceiver.State state: Long = this.state, isAirplaneModeEnabled: Boolean = this.isAirplaneModeEnabled): ConnectivityInfo {
        if (isAirplaneModeEnabled) {
            when (state) {
                ConnectivityReceiver.STATE_CONNECTED -> return CONNECTED
                ConnectivityReceiver.STATE_CONNECTING -> return CONNECTING
                ConnectivityReceiver.STATE_DISCONNECTED -> return DISCONNECTED
            }
        } else {
            when (state) {
                ConnectivityReceiver.STATE_CONNECTED -> return CONNECTED_AIRPLANE
                ConnectivityReceiver.STATE_CONNECTING -> return CONNECTING_AIRPLANE
                ConnectivityReceiver.STATE_DISCONNECTED -> return DISCONNECTED_AIRPLANE
            }
        }
        throw IllegalArgumentException("Unrecognized state $state.")
    }
}
