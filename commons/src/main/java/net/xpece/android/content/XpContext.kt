@file:JvmName("XpContext")
@file:JvmMultifileClass

package net.xpece.android.content

import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.res.ColorStateList
import android.location.LocationManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.support.annotation.LayoutRes
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.NotificationCompat
import android.telephony.TelephonyManager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import net.xpece.android.R

private val TYPED_VALUE = ThreadLocal<TypedValue>()

@UiThread
fun Context.ensureRuntimeTheme() {
    theme.resolveAttribute(R.attr.ltRuntimeTheme, getTypedValue(), true)
    val resourceId = resolveResourceId(R.attr.ltRuntimeTheme, 0)
    if (resourceId != 0) {
        setTheme(resourceId)
    }
}

private fun getTypedValue(): TypedValue {
    var typedValue = TYPED_VALUE.get()
    if (typedValue == null) {
        typedValue = TypedValue()
        TYPED_VALUE.set(typedValue)
    }
    return typedValue
}

fun <T : Activity> Context.startActivity(activity: Class<T>, func: Intent.() -> Unit = {}) {
    val intent = Intent(this, activity)
    intent.func()
    startActivity(intent)
}

fun <T : Activity> Fragment.startActivity(activity: Class<T>, func: Intent.() -> Unit = {}) {
    val intent = Intent(this.context, activity)
    intent.func()
    startActivity(intent)
}

fun <T : Activity> android.app.Fragment.startActivity(activity: Class<T>, func: Intent.() -> Unit = {}) {
    val intent = Intent(this.activity, activity)
    intent.func()
    startActivity(intent)
}

fun <T : Activity> Context.createIntent(activity: Class<T>, func: Intent.() -> Unit = {}): Intent {
    val intent = Intent(this, activity)
    intent.func()
    return intent
}

fun Context.view(uri: String, func: Intent.() -> Unit = {})
        = view(Uri.parse(uri), func)

fun Context.view(uri: Uri, func: Intent.() -> Unit = {}) {
    val i = viewIntent(uri, func)
    if (!maybeStartActivity(i)) {
        showNoActivityError(this)
    }
}

fun viewIntent(uri: String, func: Intent.() -> Unit): Intent
        = viewIntent(Uri.parse(uri)!!, func)

fun viewIntent(uri: Uri, func: Intent.() -> Unit): Intent {
    val i = Intent(Intent.ACTION_VIEW, uri)
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    i.func()
    return i
}

@JvmOverloads
fun Context.openPlayStore(packageName: String = this.packageName)
        = view(getPlayStoreUri(packageName))

@JvmOverloads
fun Context.openPlayStoreIntent(packageName: String = this.packageName, func: Intent.() -> Unit = {}): Intent
        = viewIntent(getPlayStoreUri(packageName), func)

inline fun getPlayStoreUri(packageName: String) = Uri.parse("http://play.google.com/store/apps/details?id=$packageName")!!

@UiThread
inline fun Context.getLayoutInflater(): LayoutInflater =
        LayoutInflater.from(this)

@UiThread
inline fun Context.inflate(@LayoutRes layout: Int): View =
        getLayoutInflater().inflate(layout, null, false)

@UiThread
@JvmOverloads
inline fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = true): View =
        context.getLayoutInflater().inflate(layout, this, attachToRoot)

inline fun Context.notification(func: NotificationCompat.Builder.() -> Unit): NotificationCompat.Builder {
    val builder = NotificationCompat.Builder(this)
    builder.func()
    return builder
}

inline val Context.notificationManager: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)
inline val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
inline val Context.audioManager: AudioManager
    get() = getSystemService(Context.AUDIO_SERVICE) as AudioManager
inline val Context.inputMethodManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
inline val Context.locationManager: LocationManager
    get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager
inline val Context.powerManager: PowerManager
    get() = getSystemService(Context.POWER_SERVICE) as PowerManager
inline val Context.alarmManager: AlarmManager
    get() = getSystemService(Context.ALARM_SERVICE) as AlarmManager
inline val Context.telephonyManager: TelephonyManager?
    get() = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?

val Context.isRtl: Boolean
    get() = if (Build.VERSION.SDK_INT < 17) {
        false
    } else {
        resources.configuration.layoutDirection == ViewCompat.LAYOUT_DIRECTION_RTL
    }

val Context.isDebugBuild: Boolean
    get() = 0 != (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)

inline val Context.colorPrimary: ColorStateList
    get() = resolveColorStateList(R.attr.colorPrimary)!!

inline val Context.colorAccent: ColorStateList
    get() = resolveColorStateList(R.attr.colorAccent)!!

inline val Context.colorControlNormal: ColorStateList
    get() = resolveColorStateList(R.attr.colorControlNormal)!!
