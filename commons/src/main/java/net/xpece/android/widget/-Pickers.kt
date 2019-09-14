@file:JvmName("-Pickers")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.widget

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.widget.DatePicker
import android.widget.NumberPicker
import android.widget.TimePicker

inline fun NumberPicker.setSelectionDivider(divider: Drawable?) {
    XpNumberPicker.setSelectionDivider(this, divider)
}

inline fun NumberPicker.setSelectionDividerTint(colorStateList: ColorStateList?) {
    XpNumberPicker.setSelectionDividerTint(this, colorStateList)
}

inline fun TimePicker.setSelectionDividerTint(colorStateList: ColorStateList?) {
    XpTimePicker.setSelectionDividerTint(this, colorStateList)
}

inline fun DatePicker.setSelectionDividerTint(colorStateList: ColorStateList?) {
    XpDatePicker.setSelectionDividerTint(this, colorStateList)
}
