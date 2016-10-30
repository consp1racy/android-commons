@file:JvmName("XpTextView")

package net.xpece.android.widget

import android.support.annotation.StyleRes
import android.support.v4.widget.TextViewCompat
import android.widget.TextView

/**
 * Created by Eugen on 30.10.2016.
 */

fun TextView.setTextAppearanceCompat(@StyleRes resId: Int) = TextViewCompat.setTextAppearance(this, resId)
