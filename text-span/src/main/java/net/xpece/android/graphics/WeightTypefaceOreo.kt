package net.xpece.android.graphics

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.SparseArray
import androidx.annotation.GuardedBy
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.collection.LongSparseArray

@RequiresApi(26)
@SuppressLint("PrivateApi")
internal object WeightTypefaceOreo {

    private val constructorLong = Typeface::class.java
        .getDeclaredConstructor(Long::class.javaPrimitiveType)
        .apply { isAccessible = true }

    private val fieldNativeInstance = Typeface::class.java
        .getDeclaredField("native_instance")
        .apply { isAccessible = true }

    private val methodNativeCreateFromTypefaceWithExactStyle = Typeface::class.java
        .getDeclaredMethod(
            "nativeCreateFromTypefaceWithExactStyle",
            Long::class.javaPrimitiveType,
            Int::class.javaPrimitiveType,
            Boolean::class.javaPrimitiveType
        )
        .apply { isAccessible = true }

    @Suppress("FunctionName")
    private fun Typeface(ni: Long): Typeface = constructorLong.newInstance(ni)

    private fun nativeCreateFromTypefaceWithExactStyle(
        ni: Long,
        @IntRange(from = 1, to = 1000) weight: Int,
        italic: Boolean
    ): Long = methodNativeCreateFromTypefaceWithExactStyle.invoke(null, ni, weight, italic) as Long

    private val Typeface.native_instance: Long
        get() = fieldNativeInstance.get(this) as Long

    /**
     * Cache for Typeface objects for weight variant. Currently max size is 3.
     */
    @GuardedBy("sWeightCacheLock")
    private val sWeightTypefaceCache = LongSparseArray<SparseArray<Typeface>>(3)
    private val sWeightCacheLock = Any()

    @JvmStatic
    fun createWeightStyle(
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

            val ni =
                nativeCreateFromTypefaceWithExactStyle(
                    base.native_instance,
                    weight,
                    italic
                )
            return Typeface(ni)
                .also { innerCache.put(key, it) }
        }
    }

}
