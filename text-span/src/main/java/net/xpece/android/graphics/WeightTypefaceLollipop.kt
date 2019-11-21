package net.xpece.android.graphics

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.SparseArray
import androidx.annotation.GuardedBy
import androidx.annotation.IntRange
import androidx.collection.LongSparseArray

@SuppressLint("PrivateApi")
internal object WeightTypefaceLollipop {

    private val constructorLong = Typeface::class.java
        .getDeclaredConstructor(Long::class.javaPrimitiveType)
        .apply { isAccessible = true }

    private val fieldNativeInstance = Typeface::class.java
        .getDeclaredField("native_instance")
        .apply { isAccessible = true }

    private val methodNativeCreateWeightAlias = Typeface::class.java
        .getDeclaredMethod(
            "nativeCreateWeightAlias",
            Long::class.javaPrimitiveType,
            Int::class.javaPrimitiveType
        )
        .apply { isAccessible = true }

    @Suppress("FunctionName")
    private fun Typeface(ni: Long): Typeface = constructorLong.newInstance(ni)

    private fun nativeCreateWeightAlias(
        ni: Long,
        @IntRange(from = 1, to = 1000) weight: Int
    ): Long = methodNativeCreateWeightAlias.invoke(null, ni, weight) as Long

    private val Typeface.native_instance: Long
        get() = fieldNativeInstance.get(this) as Long

    /**
     * Cache for Typeface objects for weight variant. Currently max size is 3.
     */
    @GuardedBy("sWeightCacheLock")
    private val sWeightTypefaceCache = LongSparseArray<SparseArray<Typeface>>(3)
    private val sWeightCacheLock = Any()

    @JvmStatic
    fun create(
        base: Typeface,
        @IntRange(from = 1, to = 1000) weight: Int,
        italic: Boolean
    ): Typeface {
        val key = weight shl 1 or if (italic) 1 else 0

        synchronized(sWeightCacheLock) {
            var innerCache: SparseArray<Typeface>? = sWeightTypefaceCache.get(base.native_instance)
            if (innerCache == null) {
                innerCache = SparseArray(4)
                sWeightTypefaceCache.put(base.native_instance, innerCache)
            } else {
                val typeface = innerCache.get(key)
                if (typeface != null) {
                    return typeface
                }
            }

            @Suppress("NAME_SHADOWING")
            val base = if (base.isItalic == italic) {
                base
            } else {
                val style = if (italic) Typeface.ITALIC else Typeface.NORMAL
                Typeface.create(base, style)
            }

            val ni = nativeCreateWeightAlias(base.native_instance, weight)
            return Typeface(ni)
                .also { innerCache.put(key, it) }
        }
    }
}
