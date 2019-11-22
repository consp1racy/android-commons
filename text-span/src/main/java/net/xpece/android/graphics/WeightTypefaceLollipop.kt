package net.xpece.android.graphics

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.graphics.Typeface.ITALIC
import android.util.Log
import android.util.SparseArray
import androidx.annotation.GuardedBy
import androidx.annotation.IntRange
import androidx.collection.LongSparseArray
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

@SuppressLint("PrivateApi")
internal object WeightTypefaceLollipop {

    private val isPrivateApiAvailable: Boolean

    private val constructorLong: Constructor<Typeface>?
    private val fieldNativeInstance: Field?
    private val methodNativeCreateWeightAlias: Method?

    init {
        var privateApiAvailable: Boolean
        var constructorLong: Constructor<Typeface>?
        var fieldNativeInstance: Field?
        var methodNativeCreateWeightAlias: Method?
        try {
            constructorLong = Typeface::class.java
                .getDeclaredConstructor(Long::class.javaPrimitiveType)
                .apply { isAccessible = true }

            fieldNativeInstance = Typeface::class.java
                .getDeclaredField("native_instance")
                .apply { isAccessible = true }

            methodNativeCreateWeightAlias = Typeface::class.java
                .getDeclaredMethod(
                    "nativeCreateWeightAlias",
                    Long::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
                .apply { isAccessible = true }

            privateApiAvailable = true
        } catch (_: Throwable) {
            Log.w("WeightTypefaceCompat", "Failed to lookup private API using reflection.")
            constructorLong = null
            fieldNativeInstance = null
            methodNativeCreateWeightAlias = null
            privateApiAvailable = false
        }
        this.constructorLong = constructorLong
        this.fieldNativeInstance = fieldNativeInstance
        this.methodNativeCreateWeightAlias = methodNativeCreateWeightAlias
        this.isPrivateApiAvailable = privateApiAvailable
    }

    @Suppress("FunctionName")
    private fun Typeface(ni: Long): Typeface = constructorLong!!.newInstance(ni)

    private fun nativeCreateWeightAlias(
        ni: Long,
        @IntRange(from = 1, to = 1000) weight: Int
    ): Long = methodNativeCreateWeightAlias!!.invoke(null, ni, weight) as Long

    private val Typeface.native_instance: Long
        get() = fieldNativeInstance!!.get(this) as Long

    @SuppressLint("WrongConstant")
    private fun adjustItalic(
        base: Typeface,
        italic: Boolean
    ): Typeface = if (base.isItalic == italic) {
        base
    } else {
        var style = base.style and ITALIC.inv()
        if (italic) style = style or ITALIC
        Typeface.create(base, style)
    }

    /**
     * Cache for Typeface objects for weight variant. Currently max size is 3.
     */
    @GuardedBy("weightCacheLock")
    private val weightTypefaceCache = LongSparseArray<SparseArray<Typeface>>(3)
    private val weightCacheLock = Any()

    fun create(
        base: Typeface,
        @IntRange(from = 1, to = 1000) weight: Int,
        italic: Boolean
    ): Typeface {
        if (!isPrivateApiAvailable) return base

        val key = weight shl 1 or if (italic) 1 else 0

        synchronized(weightCacheLock) {
            var innerCache: SparseArray<Typeface>? = weightTypefaceCache.get(base.native_instance)
            if (innerCache == null) {
                innerCache = SparseArray(4)
                weightTypefaceCache.put(base.native_instance, innerCache)
            } else {
                val typeface = innerCache.get(key)
                if (typeface != null) {
                    return typeface
                }
            }

            @Suppress("NAME_SHADOWING")
            val base = adjustItalic(base, italic)
            val ni = nativeCreateWeightAlias(base.native_instance, weight)
            return Typeface(ni)
                .also { innerCache.put(key, it) }
        }
    }
}
