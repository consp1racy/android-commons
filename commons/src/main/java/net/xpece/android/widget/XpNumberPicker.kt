@file:JvmName("XpNumberPicker")

package net.xpece.android.widget

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.NumberPicker
import androidx.core.graphics.drawable.DrawableCompat
import net.xpece.android.view.realLayoutDirection
import java.lang.reflect.Field

private object NumberPickerReflection {

    internal val selectionDividerField: Field? = try {
        NumberPicker::class.java
                .getDeclaredField("mSelectionDivider")
                .apply { isAccessible = true }
    } catch (ex: NoSuchFieldException) {
        Log.w("XpNumberPicker", "Couldn't fetch NumberPicker.mSelectionDivider field.", ex)
        null
    }

}

private var NumberPicker.selectionDivider: Drawable?
    get() {
        val f = NumberPickerReflection.selectionDividerField
        if (f == null) {
            Log.w("XpNumberPicker", "Couldn't get NumberPicker.mSelectionDivider field.")
            return null
        }
        return f.get(this) as Drawable?
    }
    set(value) {
        val old = selectionDivider
        if (old !== value) {
            val f = NumberPickerReflection.selectionDividerField
            if (f == null) {
                Log.w("XpNumberPicker", "Couldn't set NumberPicker.mSelectionDivider field.")
                return
            }
            if (old != null) {
                old.callback = null
            }
            f.set(this, value)
            if (value != null) {
                value.callback = this
                DrawableCompat.setLayoutDirection(value, realLayoutDirection)
                if (value.isStateful) {
                    value.state = drawableState
                }
            }
        }
    }

fun NumberPicker.setSelectionDividerTint(color: ColorStateList?) {
    var d = selectionDivider
    if (d != null) {
        d = DrawableCompat.wrap(d)
        DrawableCompat.setTintList(d, color)
        selectionDivider = d
    }
}
