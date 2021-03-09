@file:JvmName("BulletSpanCompatFactory")
@file:Suppress("FunctionName")
@file:RestrictTo(RestrictTo.Scope.LIBRARY)

package net.xpece.android.text.span

import android.os.Build.VERSION.SDK_INT
import android.os.Parcel
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal fun interface BulletSpanFactory {

    operator fun invoke(
        @Px gapWidth: Int,
        @ColorInt color: Int,
        wantColor: Boolean,
        @Px bulletRadius: Int,
    ): BulletSpanCompat

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    companion object {

        @get:JvmName("getInstance")
        val BulletSpanCompatFactory: BulletSpanFactory = when {
            SDK_INT >= 28 -> BulletSpanPieFactory
            else -> BulletSpanKitkatFactory
        }
    }
}

private object BulletSpanKitkatFactory : BulletSpanFactory {

    override fun invoke(
        gapWidth: Int,
        color: Int,
        wantColor: Boolean,
        bulletRadius: Int
    ): BulletSpanCompat {
        return BulletSpanKitkat(gapWidth, color, wantColor, bulletRadius)
    }
}

@RequiresApi(28)
private object BulletSpanPieFactory : BulletSpanFactory {

    override fun invoke(
        gapWidth: Int,
        color: Int,
        wantColor: Boolean,
        bulletRadius: Int
    ): BulletSpanCompat {
        return try {
            BulletSpanParcel(gapWidth, color, wantColor, bulletRadius)
        } catch (_: Throwable) {
            BulletSpanPie(gapWidth, color, wantColor, bulletRadius)
        }
    }

    private fun BulletSpanParcel(
        gapWidth: Int,
        color: Int,
        wantColor: Boolean,
        bulletRadius: Int,
    ): BulletSpanCompat {
        val parcel = Parcel.obtain()
        try {
            parcel.writeInt(gapWidth)
            parcel.writeInt(color)
            parcel.writeInt(if (wantColor) 1 else 0)
            parcel.writeInt(bulletRadius)
            parcel.setDataPosition(0)
            return BulletSpanPie(parcel)
        } finally {
            parcel.recycle();
        }
    }
}
