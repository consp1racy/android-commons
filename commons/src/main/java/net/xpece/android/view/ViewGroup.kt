@file:JvmName("XpViewGroup")

package net.xpece.android.view

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children as childrenImpl

@Deprecated(
    "Use AndroidX.",
    ReplaceWith("children.toList()", imports = ["androidx.core.view.children"])
)
val ViewGroup.children: List<View>
    get() = childrenImpl.toList()
