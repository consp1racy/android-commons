@file:JvmName("ToastCompat")
@file:Suppress("FunctionName")

package net.xpece.android.widget

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import android.os.Build.VERSION.SDK_INT
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.WindowManager.BadTokenException
import android.widget.Toast
import androidx.annotation.CheckResult
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import kotlin.annotation.AnnotationRetention.SOURCE
import android.widget.Toast as ToastImpl

/**
 * Construct an empty Toast object. You must call [setView][Toast.setView] before you
 * can call [show][Toast.show].
 *
 * @param context The context to use. Usually your [Application] or [Activity] object.
 */
@CheckResult
@JvmName("make")
fun Toast(context: Context): Toast {
    return ToastImpl(context.forSafeToast())
}

/**
 * Make a standard toast that just contains a text view.
 *
 * @param context The context to use. Usually your [Application] or [Activity] object.
 * @param text The text to show. Can be formatted text.
 * @param duration How long to display the message. Either [Toast.LENGTH_SHORT] or
 * [Toast.LENGTH_LONG]
 */
@CheckResult
@JvmName("makeText")
fun ToastCompat(context: Context, text: CharSequence, @Duration duration: Int): Toast {
    return Toast.makeText(context.forSafeToast(), text, duration)
}

/**
 * Make a standard toast that just contains a text view with the text from a resource.
 *
 * @param context The context to use. Usually your [Application] or [Activity] object.
 * @param resId The resource id of the string resource to use. Can be formatted text.
 * @param duration How long to display the message. Either [Toast.LENGTH_SHORT] or
 * [Toast.LENGTH_LONG]
 * @throws Resources.NotFoundException if the resource can't be found.
 */
@CheckResult
@JvmName("makeText")
fun ToastCompat(context: Context, @StringRes resId: Int, @Duration duration: Int): Toast {
    return Toast.makeText(context.forSafeToast(), resId, duration)
}

@IntDef(Toast.LENGTH_SHORT, Toast.LENGTH_LONG)
@Retention(SOURCE)
private annotation class Duration

private fun Context.forSafeToast(): Context = when (SDK_INT) {
    25 -> SafeToastContext(this)
    else -> this
}

private class SafeToastContext(base: Context) : ContextWrapper(base) {

    override fun getSystemService(name: String): Any = when (name) {
        WINDOW_SERVICE -> {
            val impl = super.getSystemService(name) as WindowManager
            SafeToastWindowManager(impl)
        }
        else -> {
            super.getSystemService(name)
        }
    }

    override fun getApplicationContext(): Context {
        return SafeToastContext(super.getApplicationContext())
    }
}

private class SafeToastWindowManager(
    private val base: WindowManager,
    private val onBadTokenException: Runnable? = null
) : WindowManager by base {

    override fun addView(view: View, params: ViewGroup.LayoutParams) {
        try {
            base.addView(view, params)
        } catch (ex: BadTokenException) {
            onBadTokenException?.run()
        }
    }
}
