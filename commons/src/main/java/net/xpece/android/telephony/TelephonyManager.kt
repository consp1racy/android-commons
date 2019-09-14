@file:JvmName("XpTelephonyManager")

package net.xpece.android.telephony

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import java.lang.reflect.Method

internal object TelephonyManagerReflection {

    @SuppressLint("PrivateApi")
    @JvmField
    val methodGetDataEnabled: Method = TelephonyManager::class.java
            .getDeclaredMethod("getDataEnabled")
            .apply { isAccessible = true }
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

@Deprecated("This method provides no meaningful value. Determine state based on your use case.")
inline val TelephonyManager.isCallCapableInstantly: Boolean
    get() = isVoiceCapableCompat && isPhone && isSimStateReady && isCallStateIdle
