@file:JvmName("XpActivityManager")

package net.xpece.android.app

import android.app.ActivityManager
import android.app.Service

@Suppress("DEPRECATION")
inline fun <reified T : Service> ActivityManager.getServiceInfo(): ActivityManager.RunningServiceInfo? =
        getRunningServices(Integer.MAX_VALUE).firstOrNull {
            T::class.java.name == it.service.className
        }
