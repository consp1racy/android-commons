@file:JvmName("XpNet")

package net.xpece.android.net

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresPermission
import net.xpece.android.app.connectivityManager

val Context.isAirplaneModeOn: Boolean
    get() = Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0

@Suppress("DEPRECATION")
val Context.isAnyNetworkConnected: Boolean
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    get() = connectivityManager?.activeNetworkInfo?.isConnected ?: false
