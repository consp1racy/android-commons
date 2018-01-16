@file:JvmName("XpViewGroup")

package net.xpece.android.view

import android.view.View
import android.view.ViewGroup

@Deprecated("Replace with anko.")
val ViewGroup.children: List<View>
    get() = (0 until childCount).map { getChildAt(it) }
