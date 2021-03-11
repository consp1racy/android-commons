package net.xpece.android.graphics

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.Typeface.*
import android.util.Log
import android.util.SparseArray
import androidx.annotation.GuardedBy
import androidx.annotation.IntRange
import androidx.annotation.RestrictTo
import androidx.core.content.res.FontResourcesParserCompat
import androidx.core.graphics.TypefaceCompat
import androidx.core.graphics.TypefaceCompat.createFromResourcesFontFile
import androidx.core.graphics.TypefaceCompat.findFromCache
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.math.abs

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal object WeightTypefaceLegacy {

    private val isPrivateApiAvailable: Boolean

    private val fieldTypefaceCompatImpl: Field?
    private val methodGetFontFamily: Method?
    private val methodAddFontFamily: Method?
    private val fieldNativeInstance: Field?

    init {
        var privateApiAvailable: Boolean
        var fieldTypefaceCompatImpl: Field?
        var methodGetFontFamily: Method?
        var methodAddFontFamily: Method?
        var fieldNativeInstance: Field?
        try {
            fieldTypefaceCompatImpl = TypefaceCompat::class.java
                .getDeclaredField("sTypefaceCompatImpl")
                .apply { isAccessible = true }

            val classTypefaceBaseCompatImpl =
                Class.forName("androidx.core.graphics.TypefaceCompatBaseImpl")

            methodGetFontFamily = classTypefaceBaseCompatImpl
                .getDeclaredMethod("getFontFamily", Typeface::class.java)
                .apply { isAccessible = true }

            methodAddFontFamily = classTypefaceBaseCompatImpl
                .getDeclaredMethod(
                    "addFontFamily",
                    Typeface::class.java,
                    FontResourcesParserCompat.FontFamilyFilesResourceEntry::class.java
                )
                .apply { isAccessible = true }

            fieldNativeInstance = Typeface::class.java
                .getDeclaredField("native_instance")
                .apply { isAccessible = true }

            privateApiAvailable = true
        } catch (_: Throwable) {
            Log.w("XpTypeface", "Failed to lookup private API using reflection.")
            fieldTypefaceCompatImpl = null
            methodGetFontFamily = null
            methodAddFontFamily = null
            fieldNativeInstance = null
            privateApiAvailable = false
        }
        this.fieldTypefaceCompatImpl = fieldTypefaceCompatImpl
        this.methodGetFontFamily = methodGetFontFamily
        this.methodAddFontFamily = methodAddFontFamily
        this.fieldNativeInstance = fieldNativeInstance
        this.isPrivateApiAvailable = privateApiAvailable
    }

    private var typefaceCompatImpl: Any = this
        get() {
            if (field === this) {
                field = fieldTypefaceCompatImpl!!.get(null)!!
            }
            return field
        }
        set(value) {
            check(field === this)
            field = value
        }

    private fun getFontFamily(typeface: Typeface): FontResourcesParserCompat.FontFamilyFilesResourceEntry? {
        return methodGetFontFamily!!.invoke(typefaceCompatImpl, typeface)
                as FontResourcesParserCompat.FontFamilyFilesResourceEntry?
    }

    private fun addFontFamily(
        typeface: Typeface?,
        entry: FontResourcesParserCompat.FontFamilyFilesResourceEntry
    ) {
        methodAddFontFamily!!.invoke(typefaceCompatImpl, typeface, entry)
    }

    private val Typeface.native_instance: Int
        get() = fieldNativeInstance!!.get(this) as Int

    private interface StyleExtractor<T> {
        fun getWeight(t: T): Int
        fun isItalic(t: T): Boolean
    }

    private fun <T> findBestFont(
        fonts: Array<T>,
        @IntRange(from = 1, to = 1000) targetWeight: Int,
        isTargetItalic: Boolean,
        extractor: StyleExtractor<T>
    ): T? {
        var best: T? = null
        var bestScore = Integer.MAX_VALUE  // smaller is better

        for (font in fonts) {
            val score = abs(extractor.getWeight(font) - targetWeight) * 2 +
                    if (extractor.isItalic(font) == isTargetItalic) 0 else 1

            if (best == null || bestScore > score) {
                best = font
                bestScore = score
            }
        }
        return best
    }

    private fun findBestEntry(
        entry: FontResourcesParserCompat.FontFamilyFilesResourceEntry,
        @IntRange(from = 1, to = 1000) weight: Int,
        italic: Boolean
    ): FontResourcesParserCompat.FontFileResourceEntry? = findBestFont(
        entry.entries,
        weight,
        italic,
        object : StyleExtractor<FontResourcesParserCompat.FontFileResourceEntry> {
            @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
            override fun getWeight(entry: FontResourcesParserCompat.FontFileResourceEntry): Int {
                return entry.weight
            }

            @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
            override fun isItalic(entry: FontResourcesParserCompat.FontFileResourceEntry): Boolean {
                return entry.isItalic
            }
        }
    )

    @SuppressLint("RestrictedApi")
    private fun createFromFontFamilyFilesResourceEntry(
        context: Context,
        entry: FontResourcesParserCompat.FontFamilyFilesResourceEntry,
        resources: Resources,
        @IntRange(from = 1, to = 1000) weight: Int,
        italic: Boolean
    ): Typeface? {
        val best = findBestEntry(entry, weight, italic) ?: return null
        // best now represents a precise font resource (not XML family).
        // Try ITALIC or NORMAL first, depending on the font entry.
        // It might also have been originally resolved under | BOLD.
        val style = if (best.isItalic) ITALIC else NORMAL
        val typeface = findFromCache(resources, best.resourceId, style)
            ?: findFromCache(resources, best.resourceId, style or BOLD)
            ?: createFromResourcesFontFile(context, resources, best.resourceId, null, style)

        addFontFamily(typeface, entry)

        return typeface
    }

    private fun getBestFontFromFamily(
        context: Context,
        typeface: Typeface,
        @IntRange(from = 1, to = 1000) weight: Int,
        italic: Boolean
    ): Typeface? {
        val families = getFontFamily(typeface) ?: return null

        return createFromFontFamilyFilesResourceEntry(
            context,
            families,
            context.resources,
            weight,
            italic
        )
    }

    /**
     * Cache for Typeface objects for weight variant. Currently max size is 3.
     */
    @GuardedBy("weightCacheLock")
    private val weightTypefaceCache = SparseArray<SparseArray<Typeface>>(3)
    private val weightCacheLock = Any()

    fun create(
        context: Context,
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

            return (getBestFontFromFamily(context, base, weight, italic) ?: base)
                .also { innerCache.put(key, it) }
        }
    }
}
