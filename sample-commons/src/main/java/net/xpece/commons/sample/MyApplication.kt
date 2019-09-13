package net.xpece.commons.sample

import android.app.Application
import android.os.Build
import net.xpece.android.app.bluetoothManager
import net.xpece.android.app.connectivityManager
import net.xpece.android.app.telephonyManager
import net.xpece.android.app.wifiManager
import net.xpece.android.util.td
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        val wm = wifiManager
        td { "Got $wm." }

        val cm = connectivityManager
        td { "Got $cm." }

        val tm = telephonyManager
        td { "Got $tm." }

        if (Build.VERSION.SDK_INT >= 18) {
            val bm = bluetoothManager
            td { "Got $bm." }
        }
    }
}
