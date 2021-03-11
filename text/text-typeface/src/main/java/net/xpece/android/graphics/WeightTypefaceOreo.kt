package net.xpece.android.graphics

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.Log
import android.util.SparseArray
import androidx.annotation.GuardedBy
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.annotation.RestrictTo
import androidx.collection.LongSparseArray
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

@RestrictTo(RestrictTo.Scope.LIBRARY)
@RequiresApi(26)
@SuppressLint("PrivateApi")
internal object WeightTypefaceOreo {

    private val isPrivateApiAvailable: Boolean

    private val constructorLong: Constructor<Typeface>?
    private val fieldNativeInstance: Field?
    private val methodNativeCreateFromTypefaceWithExactStyle: Method?

    init {
        var privateApiAvailable: Boolean
        var constructorLong: Constructor<Typeface>?
        var fieldNativeInstance: Field?
        var methodNativeCreateFromTypefaceWithExactStyle: Method?
        try {
            constructorLong = Typeface::class.java
                .getDeclaredConstructor(Long::class.javaPrimitiveType)
                .apply { isAccessible = true }

            fieldNativeInstance = Typeface::class.java
                .getDeclaredField("native_instance")
                .apply { isAccessible = true }

            methodNativeCreateFromTypefaceWithExactStyle = Typeface::class.java
                .getDeclaredMethod(
                    "nativeCreateFromTypefaceWithExactStyle",
                    Long::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType,
                    Boolean::class.javaPrimitiveType
                )
                .apply { isAccessible = true }

            privateApiAvailable = true
        } catch (_: Throwable) {
            Log.w("XpTypeface", "Failed to lookup private API using reflection.")
            constructorLong = null
            fieldNativeInstance = null
            methodNativeCreateFromTypefaceWithExactStyle = null
            privateApiAvailable = false
        }
        this.constructorLong = constructorLong
        this.fieldNativeInstance = fieldNativeInstance
        this.methodNativeCreateFromTypefaceWithExactStyle =
            methodNativeCreateFromTypefaceWithExactStyle
        this.isPrivateApiAvailable = privateApiAvailable
    }

    @Suppress("FunctionName")
    private fun Typeface(ni: Long): Typeface = constructorLong!!.newInstance(ni)

    private fun nativeCreateFromTypefaceWithExactStyle(
        ni: Long,
        @IntRange(from = 1, to = 1000) weight: Int,
        italic: Boolean
    ): Long = methodNativeCreateFromTypefaceWithExactStyle!!.invoke(null, ni, weight, italic) as Long

    private val Typeface.native_instance: Long
        get() = fieldNativeInstance!!.get(this) as Long

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

            val ni = nativeCreateFromTypefaceWithExactStyle(base.native_instance, weight, italic)
            return Typeface(ni)
                .also { innerCache.put(key, it) }
        }
    }
}
