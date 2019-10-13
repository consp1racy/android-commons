@file:JvmName("XpDatePicker")

package net.xpece.android.picker.widget

import android.content.res.ColorStateList
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import android.widget.NumberPicker
import androidx.annotation.ColorInt
import java.lang.reflect.Field

private object DatePickerReflection {

    internal val delegateClass: Class<out Any>?
    internal val delegateField: Field?

    internal val daySpinnerField: Field?
    internal val monthSpinnerField: Field?
    internal val yearSpinnerField: Field?

    init {
        val implClass: Class<out Any>?
        if (Build.VERSION.SDK_INT < 21) {
            delegateField = null
            delegateClass = null
            implClass = DatePicker::class.java
        } else {
            delegateField = DatePicker::class.java.getOptionalPrivateField("mDelegate")
            val delegateName = if (Build.VERSION.SDK_INT >= 24) {
                "android.widget.DatePickerSpinnerDelegate"
            } else {
                "android.widget.DatePicker\$DatePickerSpinnerDelegate"
            }
            delegateClass = try {
                Class.forName(delegateName)
            } catch (ex: ClassNotFoundException) {
                Log.w("XpDatePicker", "Couldn't fetch $delegateName class.", ex)
                null
            }
            implClass = delegateClass
        }
        if (implClass != null) {
            daySpinnerField = implClass.getOptionalPrivateField("mDaySpinner")
            monthSpinnerField = implClass.getOptionalPrivateField("mMonthSpinner")
            yearSpinnerField = implClass.getOptionalPrivateField("mYearSpinner")
        } else {
            daySpinnerField = null
            monthSpinnerField = null
            yearSpinnerField = null
        }
    }

    private fun <T> Class<T>.getOptionalPrivateField(name: String): Field? = try {
        getDeclaredField(name)
                .apply { isAccessible = true }
    } catch (ex: NoSuchFieldException) {
        Log.w("XpDatePicker", "Couldn't fetch $simpleName.$name field.", ex)
        null
    }
}

private fun DatePicker.getDelegate(): Any? = if (DatePickerReflection.delegateField != null) {
    DatePickerReflection.delegateField.get(this)
} else {
    Log.w("XpDatePicker", "Couldn't get DatePicker.mDelegate field.")
    null
}

private fun Any.getDaySpinner(): NumberPicker? = if (DatePickerReflection.daySpinnerField != null) {
    DatePickerReflection.daySpinnerField.get(this) as NumberPicker?
} else {
    Log.w("XpDatePicker", "Couldn't get DatePickerSpinnerDelegate.mDaySpinner field.")
    null
}

private fun Any.getMonthSpinner(): NumberPicker? = if (DatePickerReflection.monthSpinnerField != null) {
    DatePickerReflection.monthSpinnerField.get(this) as NumberPicker?
} else {
    Log.w("XpDatePicker", "Couldn't get DatePickerSpinnerDelegate.mMonthSpinner field.")
    null
}

private fun Any.getYearSpinner(): NumberPicker? = if (DatePickerReflection.yearSpinnerField != null) {
    DatePickerReflection.yearSpinnerField.get(this) as NumberPicker?
} else {
    Log.w("XpDatePicker", "Couldn't get DatePickerSpinnerDelegate.mYearSpinner field.")
    null
}

/**
 * Used to change classic [DatePicker] selection divider color on-the-fly.
 *
 * This is primarily used on Android 4.x with AppCompat, so that the selection divider color
 * is `colorControlNormal` instead of Holo blue.
 *
 * You should rely on `android:colorControlNormal` on Android 5 and newer.
 *
 * **WARNING!** This method doesn't work on Android 10 and newer. There is no alternative.
 */
@Suppress("DEPRECATION")
fun DatePicker.setSelectionDividerTintCompat(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= 29) return

    this@setSelectionDividerTint.setSelectionDividerTintCompat(ColorStateList.valueOf(color))
}

/**
 * Used to change classic [DatePicker] selection divider color on-the-fly.
 *
 * This is primarily used on Android 4.x with AppCompat, so that the selection divider color
 * is `colorControlNormal` instead of Holo blue.
 *
 * You should rely on `android:colorControlNormal` on Android 5 and newer.
 *
 * **WARNING!** This method doesn't work on Android 10 and newer. There is no alternative.
 */
@Suppress("DEPRECATION")
fun DatePicker.setSelectionDividerTintCompat(color: ColorStateList?) {
    if (Build.VERSION.SDK_INT >= 29) return

    val impl = if (Build.VERSION.SDK_INT < 21) {
        this
    } else {
        getDelegate()?.takeIf { javaClass == DatePickerReflection.delegateClass }
    }
    impl?.apply {
        getYearSpinner()?.setSelectionDividerTintCompat(color)
        getMonthSpinner()?.setSelectionDividerTintCompat(color)
        getDaySpinner()?.setSelectionDividerTintCompat(color)
    }
}
