@file:JvmName("XpTimePicker")

package net.xpece.android.widget

import android.content.res.ColorStateList
import android.os.Build
import android.util.Log
import android.widget.NumberPicker
import android.widget.TimePicker
import java.lang.reflect.Field

private object TimePickerReflection {

    internal val delegateClass: Class<out Any>?
    internal val delegateField: Field?

    internal val minuteSpinnerField: Field?
    internal val hourSpinnerField: Field?
    internal val amPmSpinnerField: Field?

    init {
        val implClass: Class<out Any>?
        if (Build.VERSION.SDK_INT < 21) {
            delegateField = null
            delegateClass = null
            implClass = TimePicker::class.java
        } else {
            delegateField = TimePicker::class.java.getOptionalPrivateField("mDelegate")
            val delegateName = if (Build.VERSION.SDK_INT == 21) {
                // On Android 5.0 the two delegate class names are switched.
                "android.widget.TimePickerClockDelegate"
            } else {
                "android.widget.TimePickerSpinnerDelegate"
            }
            delegateClass = try {
                Class.forName(delegateName)
            } catch (ex: ClassNotFoundException) {
                Log.w("XpTimePicker", "Couldn't fetch $delegateName class.", ex)
                null
            }
            implClass = delegateClass
        }
        if (implClass != null) {
            minuteSpinnerField = implClass.getOptionalPrivateField("mMinuteSpinner")
            hourSpinnerField = implClass.getOptionalPrivateField("mHourSpinner")
            amPmSpinnerField = implClass.getOptionalPrivateField("mAmPmSpinner")
        } else {
            minuteSpinnerField = null
            hourSpinnerField = null
            amPmSpinnerField = null
        }
    }

    private fun <T> Class<T>.getOptionalPrivateField(name: String): Field? = try {
        getDeclaredField(name)
                .apply { isAccessible = true }
    } catch (ex: NoSuchFieldException) {
        Log.w("XpTimePicker", "Couldn't fetch $simpleName.$name field.", ex)
        null
    }
}

private fun TimePicker.getDelegate(): Any? = if (TimePickerReflection.delegateField != null) {
    TimePickerReflection.delegateField.get(this)
} else {
    Log.w("XpTimePicker", "Couldn't get TimePicker.mDelegate field.")
    null
}

private fun Any.getDaySpinner(): NumberPicker? = if (TimePickerReflection.minuteSpinnerField != null) {
    TimePickerReflection.minuteSpinnerField.get(this) as NumberPicker?
} else {
    Log.w("XpTimePicker", "Couldn't get TimePickerSpinnerDelegate.mMinuteSpinner field.")
    null
}

private fun Any.getMonthSpinner(): NumberPicker? = if (TimePickerReflection.hourSpinnerField != null) {
    TimePickerReflection.hourSpinnerField.get(this) as NumberPicker?
} else {
    Log.w("XpTimePicker", "Couldn't get TimePickerSpinnerDelegate.mHourSpinner field.")
    null
}

private fun Any.getYearSpinner(): NumberPicker? = if (TimePickerReflection.amPmSpinnerField != null) {
    TimePickerReflection.amPmSpinnerField.get(this) as NumberPicker?
} else {
    Log.w("XpTimePicker", "Couldn't get TimePickerSpinnerDelegate.mAmPmSpinner field.")
    null
}

fun TimePicker.setSelectionDividerTint(color: ColorStateList?) {
    val impl = if (Build.VERSION.SDK_INT < 21) {
        this
    } else {
        getDelegate()?.takeIf { javaClass == TimePickerReflection.delegateClass }
    }
    impl?.apply {
        getYearSpinner()?.setSelectionDividerTint(color)
        getMonthSpinner()?.setSelectionDividerTint(color)
        getDaySpinner()?.setSelectionDividerTint(color)
    }
}
