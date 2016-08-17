@file:JvmName("XpContext")

package net.xpece.android.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.v4.app.Fragment
import android.support.v7.content.res.AppCompatResources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.xpece.android.R

/**
 * @author Eugen on 28.07.2016.
 */

private val TYPED_VALUE = TypedValue()

@UiThread
fun Context.ensureRuntimeTheme() {
    theme.resolveAttribute(R.attr.ltRuntimeTheme, TYPED_VALUE, true)
    val resourceId = TYPED_VALUE.resourceId
    if (resourceId != 0) {
        setTheme(resourceId)
    }
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

fun <T : Activity> Context.createIntent(activity: Class<T>, func: Intent.() -> Unit = {}): Intent {
    val intent = Intent(this, activity)
    intent.func()
    return intent
}

@UiThread
fun Context.getLayoutInflater(): LayoutInflater =
        LayoutInflater.from(this)

@UiThread
fun Context.inflate(@LayoutRes layout: Int): View =
        getLayoutInflater().inflate(layout, null, false)

@UiThread
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = true): View =
        context.getLayoutInflater().inflate(layout, this, attachToRoot)

@ColorInt
fun Context.getColorCompat(@ColorRes resId: Int): Int =
        AppCompatResources.getColorStateList(this, resId)!!.defaultColor

fun Context.getColorStateListCompat(@ColorRes resId: Int): ColorStateList? =
        AppCompatResources.getColorStateList(this, resId)

fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable? =
        AppCompatResources.getDrawable(this, resId)
