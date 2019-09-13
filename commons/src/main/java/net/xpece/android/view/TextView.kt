@file:JvmName("XpTextView")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.view

import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat
import android.widget.TextView

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
