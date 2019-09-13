@file:JvmName("XpIntent")

package net.xpece.android.content

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import net.xpece.android.R

fun Context.email(address: String) {
    val i = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", address, null))
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    if (!maybeStartActivity(i)) {
        Toast.makeText(this, R.string.xpc_no_intent_handler, Toast.LENGTH_LONG).show()
    }
}

fun Context.dial(number: String) {
    val i = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    if (!maybeStartActivity(i)) {
        Toast.makeText(this, R.string.xpc_no_intent_handler, Toast.LENGTH_LONG).show()
    }
}

@RequiresPermission(Manifest.permission.CALL_PHONE)
fun Context.call(number: String) {
    val i = Intent(Intent.ACTION_CALL, Uri.fromParts("tel", number, null))
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    if (!maybeStartActivity(i)) {
        Toast.makeText(this, R.string.xpc_no_intent_handler, Toast.LENGTH_LONG).show()
    }
}

fun Context.grantUriPermission(intent: Intent, uri: Uri, modeFlags: Int) {
    val resolvedIntentActivities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    for (resolvedIntentInfo in resolvedIntentActivities) {
        val packageName = resolvedIntentInfo.activityInfo.packageName
        grantUriPermission(packageName, uri, modeFlags)
    }
}

/**
 * Attempt to launch the supplied [Intent]. Queries on-device packages before launching and
 * will display a simple message if none are available to handle it.
 */
fun Context.maybeStartActivity(intent: Intent) = maybeStartActivity(intent, false)

/**
 * Attempt to launch Android's chooser for the supplied [Intent]. Queries on-device
 * packages before launching and will display a simple message if none are available to handle
 * it.
 */
fun Context.maybeStartChooser(intent: Intent) = maybeStartActivity(intent, true)

fun Context.maybeStartActivity(intent: Intent, chooser: Boolean): Boolean {
    var intent2 = intent
    if (hasHandler(intent2)) {
        if (chooser) {
            intent2 = Intent.createChooser(intent2, null)
        }
        startActivity(intent2)
        return true
    } else {
        //            showNoActivityError(context);
        return false
    }
}

fun Activity.maybeStartActivityForResult(intent: Intent, requestCode: Int): Boolean {
    if (hasHandler(intent)) {
        startActivityForResult(intent, requestCode)
        return true
    } else {
        //            showNoActivityError(activity);
        return false
    }
}

fun androidx.fragment.app.Fragment.maybeStartActivityForResult(intent: Intent, requestCode: Int): Boolean {
    val context = context!!
    if (context.hasHandler(intent)) {
        startActivityForResult(intent, requestCode)
        return true
    } else {
        //            showNoActivityError(context);
        return false
    }
}

fun android.app.Fragment.maybeStartActivityForResult(intent: Intent, requestCode: Int): Boolean {
    val context = activity
    if (context.hasHandler(intent)) {
        startActivityForResult(intent, requestCode)
        return true
    } else {
        //            showNoActivityError(context);
        return false
    }
}

inline fun showNoActivityError(context: Context) = Toast.makeText(context, R.string.xpc_no_intent_handler, Toast.LENGTH_LONG).show()

/**
 * Queries on-device packages for a handler for the supplied [Intent].
 */
inline fun Context.hasHandler(intent: Intent) = packageManager.queryIntentActivities(intent, 0).isNotEmpty()

/**
 * Queries on-device packages for a handler for the supplied [Intent].
 */
inline fun Context.getHandlerCount(intent: Intent) = packageManager.queryIntentActivities(intent, 0).size

inline fun getMapIntent(query: String, latLng: LatLng?): Intent {
    val uri = Uri.parse("geo:+${latLng?.latitude ?: 0},${latLng?.longitude ?: 0}?q=$query")
    return Intent(Intent.ACTION_VIEW, uri)
}

fun Context.getAppDetailIntent(packageName: String = this.packageName): Intent {
    val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    return i
}

inline fun intent(context: Context, activity: Class<out Any>, func: Intent.() -> Intent) = Intent(context, activity).func()

inline private fun getMapIntent(query: String, latitude: Double, longitude: Double): Intent {
    val uri = Uri.parse("geo:+$latitude,$longitude?q=$query")
    return Intent(Intent.ACTION_VIEW, uri)
}
