@file:JvmName("XpNet")

package net.xpece.android.net

import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresPermission
import net.xpece.android.app.connectivityManager

/**
 * Gets the state of Airplane Mode.
 * @return true if enabled.
 */
val Context.isAirplaneModeOn: Boolean
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    get() = if (Build.VERSION.SDK_INT < 17) {
        Settings.System.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    } else {
        Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }

val Context.isAnyNetworkConnected: Boolean
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    get() = connectivityManager?.activeNetworkInfo?.isConnected ?: false
