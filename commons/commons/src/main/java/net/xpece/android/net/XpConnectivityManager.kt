@file:JvmName("XpConnectivityManager")

package net.xpece.android.net

import android.Manifest
import android.annotation.SuppressLint
import android.net.ConnectivityManager
import androidx.annotation.RequiresPermission
import java.lang.reflect.Method

internal object ConnectivityManagerReflection {

    @SuppressLint("PrivateApi")
    @JvmField
    val methodGetMobileDataEnabled: Method =
        ConnectivityManager::class.java
            .getDeclaredMethod("getMobileDataEnabled")
            .apply { isAccessible = true }
}

val ConnectivityManager.isMobileDataEnabled: Boolean
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    get() = ConnectivityManagerReflection.methodGetMobileDataEnabled.invoke(this) as Boolean
