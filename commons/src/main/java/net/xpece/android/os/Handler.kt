@file:JvmName("-Handler")

package net.xpece.android.os

import android.os.Handler
import android.os.SystemClock

@Deprecated(
    "Reorder parameters. Use Long. Use AndroidX.",
    ReplaceWith(
        "postDelayed(delayMillis.toLong(), r)",
        imports = ["androidx.core.os.postDelayed"]
    )
)
fun Handler.postDelayed(r: Runnable, delayMillis: Int) =
    postDelayed(r, delayMillis.toLong())

@Deprecated(
    "Use AndroidX.",
    ReplaceWith(
        "postDelayed(delayMillis.toLong(), runnable)",
        imports = ["androidx.core.os.postDelayed"]
    )
)
fun Handler.postDelayed(delayMillis: Int, runnable: () -> Unit) =
    this.postDelayed(runnable, delayMillis.toLong())

@Deprecated(
    "Use AndroidX.",
    ReplaceWith(
        "postDelayed(delayMillis, runnable)",
        imports = ["androidx.core.os.postDelayed"]
    )
)
fun Handler.postDelayed(delayMillis: Long, runnable: () -> Unit) =
    this.postDelayed(runnable, delayMillis)

@Deprecated(
    "Use AndroidX.",
    ReplaceWith(
        "postDelayed(delayMillis.toLong(), token, runnable)",
        imports = ["androidx.core.os.postDelayed"]
    )
)
fun Handler.postDelayed(token: Any, delayMillis: Int, r: () -> Unit) =
    postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis)

@Deprecated(
    "Use AndroidX.",
    ReplaceWith(
        "postDelayed(delayMillis, token, runnable)",
        imports = ["androidx.core.os.postDelayed"]
    )
)
fun Handler.postDelayed(token: Any, delayMillis: Long, r: () -> Unit) =
    postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis)

@Deprecated(
    "Declared by SDK API 28. Reorder parameters. Use AndroidX.",
    ReplaceWith(
        "postDelayed(delayMillis.toLong(), token, r)",
        imports = ["androidx.core.os.postDelayed"]
    ),
    DeprecationLevel.HIDDEN
)
fun Handler.postDelayed(r: Runnable, token: Any, delayMillis: Int) =
    postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis)

@Deprecated(
    "Declared by SDK API 28. Reorder parameters. Use AndroidX.",
    ReplaceWith(
        "postDelayed(delayMillis.toLong(), token, r)",
        imports = ["androidx.core.os.postDelayed"]
    ),
    DeprecationLevel.HIDDEN
)
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
fun Handler.postDelayed(r: Runnable, token: Any, delayMillis: Long) =
    postAtTime(r, token, SystemClock.uptimeMillis() + delayMillis)
