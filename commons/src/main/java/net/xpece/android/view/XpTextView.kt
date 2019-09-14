@file:JvmName("XpTextView")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.view

import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat

inline fun TextView.setTextAppearanceCompat(@StyleRes resId: Int) =
        TextViewCompat.setTextAppearance(this, resId)

@JvmOverloads
fun TextView.setTextAndVisibility(text: CharSequence?, invisible: Boolean = false) {
    if (text.isNullOrBlank()) {
        if (invisible) invisible() else gone()
        setText(null)
    } else {
        setText(text)
        visible()
    }
}
