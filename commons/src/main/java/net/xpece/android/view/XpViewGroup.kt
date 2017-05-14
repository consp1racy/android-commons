package net.xpece.android.view

import android.view.View
import android.view.ViewGroup

/**
 * Created by Eugen on 14.05.2017.
 */

val ViewGroup.children: List<View>
    get() = (0..childCount - 1).map { getChildAt(it) }
