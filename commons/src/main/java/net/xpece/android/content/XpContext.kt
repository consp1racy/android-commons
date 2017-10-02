@file:JvmName("XpContext")
@file:JvmMultifileClass
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.support.annotation.LayoutRes
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.app.NotificationCompat
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import net.xpece.android.R
import net.xpece.android.app.layoutInflater

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

inline fun <reified T : Activity> Context.startActivity() {
    startActivity<T> { }
}

inline fun <reified T : Activity> Context.startActivity(func: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.func()
    startActivity(intent)
}

inline fun <reified T : Activity> Fragment.startActivity() {
    startActivity<T> { }
}

inline fun <reified T : Activity> Fragment.startActivity(func: Intent.() -> Unit) {
    val intent = Intent(this.context, T::class.java)
    intent.func()
    startActivity(intent)
}

inline fun <reified T : Activity> android.app.Fragment.startActivity() {
    startActivity<T> { }
}

inline fun <reified T : Activity> android.app.Fragment.startActivity(func: Intent.() -> Unit) {
    val intent = Intent(this.activity, T::class.java)
    intent.func()
    startActivity(intent)
}

inline fun <reified T : Activity> Context.createIntent(): Intent =
        createIntent<T> { }

inline fun <reified T : Activity> Context.createIntent(func: Intent.() -> Unit): Intent {
    val intent = Intent(this, T::class.java)
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

fun viewIntent(uri: String, func: Intent.() -> Unit = {}): Intent
        = viewIntent(Uri.parse(uri)!!, func)

fun viewIntent(uri: Uri, func: Intent.() -> Unit = {}): Intent {
    val i = Intent(Intent.ACTION_VIEW, uri)
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    i.func()
    return i
}

@JvmOverloads
inline fun Context.openPlayStore(packageName: String = this.packageName)
        = view(getPlayStoreUri(packageName))

@JvmOverloads
fun Context.openPlayStoreIntent(
        packageName: String = this.packageName, func: Intent.() -> Unit = {}): Intent
        = viewIntent(getPlayStoreUri(packageName), func)

inline fun getPlayStoreUri(packageName: String) = Uri.parse(
        "http://play.google.com/store/apps/details?id=$packageName")!!

inline fun Context.inflate(@LayoutRes layout: Int): View =
        layoutInflater.inflate(layout, null, false)

@JvmOverloads
inline fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = true): View =
        context.layoutInflater.inflate(layout, this, attachToRoot)

inline fun Context.notification(
        func: NotificationCompat.Builder.() -> Unit): NotificationCompat.Builder {
    val builder = NotificationCompat.Builder(this)
    builder.func()
    return builder
}

inline val Context.isRtl: Boolean
    get() = if (Build.VERSION.SDK_INT < 17) {
        false
    } else {
        resources.configuration.layoutDirection == ViewCompat.LAYOUT_DIRECTION_RTL
    }

inline val Context.isDebugBuild: Boolean
    get() = 0 != (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)

/**
 * @throws NullPointerException
 */
inline val Context.colorPrimary: ColorStateList
    get() = resolveColorStateList(R.attr.colorPrimary)!!

/**
 * @throws NullPointerException
 */
inline val Context.colorAccent: ColorStateList
    get() = resolveColorStateList(R.attr.colorAccent)!!

/**
 * @throws NullPointerException
 */
inline val Context.colorControlNormal: ColorStateList
    get() = resolveColorStateList(R.attr.colorControlNormal)!!
