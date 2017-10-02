package net.xpece.android.view

import android.view.View
import android.view.ViewGroup

/**
 * Created by Eugen on 14.05.2017.
 */

@Deprecated("Replace with anko.")
val ViewGroup.children: List<View>
    get() = (0 until childCount).map { getChildAt(it) }
