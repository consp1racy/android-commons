@file:JvmName("XpNet")

package net.xpece.android.net

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.provider.Settings
import net.xpece.android.app.connectivityManager

/**
 * Gets the state of Airplane Mode.
 * @return true if enabled.
 */
@get:TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
val Context.isAirplaneModeOn: Boolean
    get() = if (Build.VERSION.SDK_INT < 17) {
        Settings.System.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    } else {
        Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }

val Context.isAnyNetworkConnected: Boolean
    get() = connectivityManager?.activeNetworkInfo?.isConnected ?: false
