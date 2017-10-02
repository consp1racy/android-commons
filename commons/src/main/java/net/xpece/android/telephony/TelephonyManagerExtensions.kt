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
