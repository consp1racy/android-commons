@file:JvmName("MediumSpanCompat")
@file:Suppress("FunctionName")

package net.xpece.android.text.span

import android.graphics.Typeface
import android.os.Build.VERSION.SDK_INT
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan

private val FACTORY = when {
    SDK_INT >= 21 -> RealMediumSpanFactory
    else -> FakeMediumSpanFactory
}

@Deprecated(
    message = "Every span must be a unique instance.",
    replaceWith = ReplaceWith("MediumSpanCompat()"),
    level = DeprecationLevel.ERROR
)
@get:JvmName("getInstance")
val MediumSpanCompat: Any = FACTORY.create()

@JvmName("create")
fun MediumSpanCompat(): Any = FACTORY.create()

interface MediumSpanFactory {

    fun create(): Any
}

object FakeMediumSpanFactory : MediumSpanFactory {

    override fun create(): Any = StyleSpan(Typeface.BOLD)
}

object RealMediumSpanFactory : MediumSpanFactory {

    override fun create(): Any = TypefaceSpan("sans-serif-medium")
}
