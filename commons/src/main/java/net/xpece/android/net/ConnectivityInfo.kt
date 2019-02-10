package net.xpece.android.net

class ConnectivityInfo(@ConnectivityReceiver.State val state: Int, val isAirplaneModeEnabled: Boolean) {

    companion object {
        @JvmStatic private val CONNECTED = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_CONNECTED, false)
        @JvmStatic private val CONNECTING = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_CONNECTING, false)
        @JvmStatic private val DISCONNECTED = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_DISCONNECTED, false)
        @JvmStatic private val CONNECTED_AIRPLANE = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_CONNECTED, true)
        @JvmStatic private val CONNECTING_AIRPLANE = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_CONNECTING, true)
        @JvmStatic private val DISCONNECTED_AIRPLANE = ConnectivityInfo(ConnectivityReceiver.Companion.STATE_DISCONNECTED, true)
        @JvmField val DEFAULT = CONNECTED
    }

    val isConnected: Boolean
        get() = state == ConnectivityReceiver.STATE_CONNECTED

    val isConnecting: Boolean
        get() = state == ConnectivityReceiver.STATE_CONNECTING

    val isDisconnected: Boolean
        get() = state == ConnectivityReceiver.STATE_DISCONNECTED

    fun copy(@ConnectivityReceiver.State state: Int = this.state, isAirplaneModeEnabled: Boolean = this.isAirplaneModeEnabled): ConnectivityInfo {
        if (!isAirplaneModeEnabled) {
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ConnectivityInfo) return false

        if (state != other.state) return false
        if (isAirplaneModeEnabled != other.isAirplaneModeEnabled) return false

        return true
    }

    override fun hashCode(): Int {
        var result = state.hashCode()
        result = 31 * result + isAirplaneModeEnabled.hashCode()
        return result
    }

    override fun toString(): String {
        return "ConnectivityInfo(state=$state, isAirplaneModeEnabled=$isAirplaneModeEnabled)"
    }
}
