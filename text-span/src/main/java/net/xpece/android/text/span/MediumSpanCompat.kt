@file:JvmName("MediumSpanCompat")
@file:Suppress("FunctionName")

package net.xpece.android.text.span

import android.graphics.Typeface
import android.os.Build.VERSION.SDK_INT
import android.text.style.MetricAffectingSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan

private val MediumSpanCompatFactory = when {
    SDK_INT >= 21 -> RealMediumSpanFactory
    else -> FakeMediumSpanFactory
}

@JvmName("create")
fun MediumSpanCompat(): MetricAffectingSpan = MediumSpanCompatFactory()

private interface MediumSpanFactory {

    operator fun invoke(): MetricAffectingSpan
}

private object FakeMediumSpanFactory : MediumSpanFactory {

    override fun invoke(): MetricAffectingSpan = StyleSpan(Typeface.BOLD)
}

private object RealMediumSpanFactory : MediumSpanFactory {

    override fun invoke(): MetricAffectingSpan = TypefaceSpan("sans-serif-medium")
}
