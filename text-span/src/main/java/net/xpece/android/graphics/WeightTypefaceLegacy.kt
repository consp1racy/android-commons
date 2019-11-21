package net.xpece.android.graphics

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.util.SparseArray
import androidx.annotation.GuardedBy
import androidx.annotation.IntRange
import androidx.collection.LongSparseArray
import androidx.core.content.res.FontResourcesParserCompat
import androidx.core.graphics.TypefaceCompat
import androidx.core.graphics.TypefaceCompatUtil
import kotlin.math.abs

internal object WeightTypefaceLegacy {

    private val fieldTypefaceCompatImpl = TypefaceCompat::class.java
        .getDeclaredField("sTypefaceCompatImpl")
        .apply { isAccessible = true }

    private val classTypefaceBaseCompatImpl =
        Class.forName("androidx.core.graphics.TypefaceCompatBaseImpl")

    private val methodGetFontFamily = classTypefaceBaseCompatImpl
        .getDeclaredMethod("getFontFamily", Typeface::class.java)
        .apply { isAccessible = true }

    private val methodAddFontFamily = classTypefaceBaseCompatImpl
        .getDeclaredMethod(
            "addFontFamily",
            Typeface::class.java,
            FontResourcesParserCompat.FontFamilyFilesResourceEntry::class.java
        )
        .apply { isAccessible = true }

    private var typefaceCompatImpl: Any = WeightTypefaceLegacy
        get() {
            if (field === WeightTypefaceLegacy) {
                field = fieldTypefaceCompatImpl.get(null)!!
            }
            return field
        }

    private val fieldNativeInstance = Typeface::class.java
        .getDeclaredField("native_instance")
        .apply { isAccessible = true }

    private fun getFontFamily(typeface: Typeface): FontResourcesParserCompat.FontFamilyFilesResourceEntry? {
        return methodGetFontFamily.invoke(typefaceCompatImpl, typeface)
                as FontResourcesParserCompat.FontFamilyFilesResourceEntry?
    }

    private fun addFontFamily(
        typeface: Typeface?,
        entry: FontResourcesParserCompat.FontFamilyFilesResourceEntry
    ) {
        methodAddFontFamily.invoke(typefaceCompatImpl, typeface, entry)
    }

    private val Typeface.native_instance: Long
        get() = fieldNativeInstance.get(this) as Long

    /**
     * Used by Resources to load a font resource of type font file.
     */
    private fun createFromResourcesFontFile(
        context: Context,
        resources: Resources,
        id: Int
    ): Typeface? {
        val tmpFile = TypefaceCompatUtil.getTempFile(context) ?: return null
        return try {
            if (!TypefaceCompatUtil.copyToFile(tmpFile, resources, id)) {
                null
            } else {
                Typeface.createFromFile(tmpFile.path)
            }
        } catch (_: RuntimeException) {
            // This was thrown from Typeface.createFromFile when a Typeface could not be loaded.
            // such as due to an invalid ttf or unreadable file. We don't want to throw that
            // exception anymore.
            null
        } finally {
            tmpFile.delete()
        }
    }

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
    ): FontResourcesParserCompat.FontFileResourceEntry? =
        findBestFont(
            entry.entries,
            weight,
            italic,
            object :
                StyleExtractor<FontResourcesParserCompat.FontFileResourceEntry> {
                @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
                override fun getWeight(entry: FontResourcesParserCompat.FontFileResourceEntry): Int {
                    return entry.weight
                }

                @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
                override fun isItalic(entry: FontResourcesParserCompat.FontFileResourceEntry): Boolean {
                    return entry.isItalic
                }
            })

    private fun createFromFontFamilyFilesResourceEntry(
        context: Context,
        entry: FontResourcesParserCompat.FontFamilyFilesResourceEntry,
        resources: Resources,
        @IntRange(from = 1, to = 1000) weight: Int,
        italic: Boolean
    ): Typeface? {
        val best = findBestEntry(
            entry,
            weight,
            italic
        ) ?: return null
        val typeface = createFromResourcesFontFile(
            context,
            resources,
            best.resourceId
        )

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
    @GuardedBy("sWeightCacheLock")
    private val sWeightTypefaceCache = LongSparseArray<SparseArray<Typeface>>(3)
    private val sWeightCacheLock = Any()

    @JvmStatic
    fun create(
        context: Context,
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

            return (getBestFontFromFamily(context, base, weight, italic) ?: base)
                .also { innerCache.put(key, it) }
        }
    }
}
