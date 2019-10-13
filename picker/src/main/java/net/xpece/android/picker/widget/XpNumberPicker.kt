@file:JvmName("XpNumberPicker")

package net.xpece.android.picker.widget

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.widget.NumberPicker
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import java.lang.reflect.Field

private object NumberPickerReflection {

    val FIELD_SELECTION_DIVIDER: Field =
            NumberPicker::class.java
                    .getDeclaredField("mSelectionDivider")
                    .apply { isAccessible = true }
}

/**
 * Used to change [NumberPicker] selection divider drawable on-the-fly.
 *
 * **WARNING!** This method doesn't work on Android 10 and newer. There is no alternative.
 *
 * @see [setSelectionDividerTintCompat]
 */
@Suppress("DEPRECATION")
@Deprecated("Not available on API 29+.")
var NumberPicker.selectionDividerCompat: Drawable?
    get() = trySilent { NumberPickerReflection.FIELD_SELECTION_DIVIDER.get(this) as Drawable? }
    set(divider) {
        trySilent {
            val old = selectionDividerCompat
            if (old !== divider) {
                NumberPickerReflection.FIELD_SELECTION_DIVIDER.set(this, divider)

                old?.callback = null

                if (divider != null) {
                    divider.callback = this
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        divider.layoutDirection = layoutDirection
                    }
                    if (divider.isStateful) {
                        divider.state = drawableState
                    }
                }
            }
        }
    }

/**
 * Used to change [NumberPicker] selection divider color on-the-fly.
 *
 * This is primarily used on Android 4.x with AppCompat, so that the selection divider color
 * is `colorControlNormal` instead of Holo blue.
 *
 * You should rely on `android:colorControlNormal` on Android 5 and newer.
 *
 * **WARNING!** This method doesn't work on Android 10 and newer. There is no alternative.
 */
@Suppress("DEPRECATION")
fun NumberPicker.setSelectionDividerTintCompat(color: ColorStateList?) {
    var d = selectionDividerCompat
    if (d != null) {
        d = DrawableCompat.wrap(d)
        DrawableCompat.setTintList(d, color)
        selectionDividerCompat = d
    }
}

/**
 * Used to change [NumberPicker] selection divider color on-the-fly.
 *
 * This is primarily used on Android 4.x with AppCompat, so that the selection divider color
 * is `colorControlNormal` instead of Holo blue.
 *
 * You should rely on `android:colorControlNormal` on Android 5 and newer.
 *
 * **WARNING!** This method doesn't work on Android 10 and newer. There is no alternative.
 */
@Suppress("DEPRECATION")
fun NumberPicker.setSelectionDividerTintCompat(@ColorInt color: Int) {
    var d = selectionDividerCompat
    if (d != null) {
        d = DrawableCompat.wrap(d)
        DrawableCompat.setTint(d, color)
        selectionDividerCompat = d
    }
}

private inline fun <T> trySilent(block: () -> T): T? {
    if (Build.VERSION.SDK_INT < 29) {
        try {
            return block()
        } catch (ex: Throwable) {
            Log.e("XpNumberPicker", "Failed to access NumberPicker selection divider.", ex)
        }
    }
    return null
}
