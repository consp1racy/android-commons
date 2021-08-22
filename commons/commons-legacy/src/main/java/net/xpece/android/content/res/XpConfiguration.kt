@file:JvmName("XpConfiguration")

package net.xpece.android.content.res

import android.content.res.Configuration

@Deprecated(
    message = "Use non-compat version.",
    replaceWith = ReplaceWith("layoutDirection"),
    level = DeprecationLevel.ERROR
)
@Suppress("DEPRECATION")
val Configuration.layoutDirectionCompat: Int
    get() = layoutDirection
