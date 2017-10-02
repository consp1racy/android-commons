@file:JvmName("XpTelephonyManager")

package net.xpece.android.telephony

import android.Manifest
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.annotation.RequiresPermission
import android.telephony.TelephonyManager
import java.lang.reflect.Method

internal object TelephonyManagerReflection {
    @JvmField
    val methodGetDataEnabled: Method =
            TelephonyManager::class.java.getDeclaredMethod("getDataEnabled")

    init {
        methodGetDataEnabled.isAccessible = true
    }
}

val TelephonyManager.isDataEnabledCompat: Boolean
    @RequiresApi(21)
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    get() = if (Build.VERSION.SDK_INT >= 26) {
        isDataEnabled
    } else {
        TelephonyManagerReflection.methodGetDataEnabled.invoke(this) as Boolean
    }

inline val TelephonyManager.isPhone: Boolean
    get() = phoneType != TelephonyManager.PHONE_TYPE_NONE

inline val TelephonyManager.isSimStateReady: Boolean
    get() = simState == TelephonyManager.SIM_STATE_READY

inline val TelephonyManager.isCallStateIdle: Boolean
    get() = callState == TelephonyManager.CALL_STATE_IDLE

inline val TelephonyManager.isVoiceCapableCompat: Boolean
    get() = Build.VERSION.SDK_INT < 22 || isVoiceCapable

inline val TelephonyManager.isCallCapableInstantly: Boolean
    get() = isVoiceCapableCompat && isPhone && isSimStateReady && isCallStateIdle
