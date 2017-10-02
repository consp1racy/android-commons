package net.xpece.android.telephony

import android.os.Build
import android.telephony.TelephonyManager

val TelephonyManager.isSimStateReady: Boolean
    get() = simState == TelephonyManager.SIM_STATE_READY

val TelephonyManager.isCallStateIdle: Boolean
    get() = callState == TelephonyManager.CALL_STATE_IDLE

val TelephonyManager.isCallCapableInstantly: Boolean
    get() = (Build.VERSION.SDK_INT < 23 || phoneCount > 0) &&
            (Build.VERSION.SDK_INT < 22 || isVoiceCapable) &&
            phoneType != TelephonyManager.PHONE_TYPE_NONE &&
            simState == TelephonyManager.SIM_STATE_READY &&
            callState == TelephonyManager.CALL_STATE_IDLE
