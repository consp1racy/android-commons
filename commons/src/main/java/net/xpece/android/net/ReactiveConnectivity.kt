package net.xpece.android.net

import android.content.BroadcastReceiver
import android.content.Context
import android.support.annotation.CheckResult
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * @author Eugen on 04.01.2017.
 */

object ReactiveConnectivity {

    // Keep fresh context avaiable after Instant Run. // TODO Does this actually do anything?
    @JvmStatic private val contextToConnectivityReceiver = WeakHashMap<Context, ConnectivityReceiver>()

    @JvmStatic private fun obtainConnectivityReceiver(context: Context): ConnectivityReceiver {
        val app = context.applicationContext
        var cr = contextToConnectivityReceiver[app]
        if (cr == null) {
            cr = ConnectivityReceiver.newInstance(app)
        }
        contextToConnectivityReceiver[app] = cr
        return cr
    }

    /**
     * Creates a new [Flowable] broadcasting changes in connectivity reported by system as
     * [ConnectivityInfo] objects.
     *
     * @param context Instance of [Context] able to register [BroadcastReceiver]s.
     * @param defaultConnectivity Can be one of the following:
     * * Instance of [ConnectivityInfo] to start broadcast with that object.
     * * *Default:* Pass `null` to wait for connectivity change reported by system.
     */
    @CheckResult
    @JvmOverloads
    @JvmStatic
    fun observe(context: Context, defaultConnectivity: ConnectivityInfo? = null): Flowable<ConnectivityInfo> {
        val connectivitySubject = PublishSubject.create<ConnectivityInfo>()

        val connectivityReceiver = obtainConnectivityReceiver(context)
        val connectivityCallback = ConnectivityCallback {
            connectivitySubject.onNext(it)
        }
        connectivityReceiver.register(connectivityCallback)

        var flowable = connectivitySubject.toFlowable(BackpressureStrategy.LATEST).doOnCancel {
            connectivityReceiver.unregister(connectivityCallback)
        }
        if (defaultConnectivity != null) {
            flowable = flowable.startWith(defaultConnectivity)
        }
        return flowable.distinctUntilChanged()
    }
}
