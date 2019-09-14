@file:JvmName("XpParcel")

package net.xpece.android.os

import android.annotation.SuppressLint
import android.os.Parcel

@Suppress("NOTHING_TO_INLINE")
@Deprecated(
        "Shadows method since API 29.",
        replaceWith = ReplaceWith("writeBooleanCompat"),
        level = DeprecationLevel.HIDDEN
)
inline fun Parcel.writeBoolean(value: Boolean) = writeBooleanCompat(value)

@Suppress("NOTHING_TO_INLINE")
@Deprecated(
        "Shadows method since API 29.",
        replaceWith = ReplaceWith("readBooleanCompat"),
        level = DeprecationLevel.HIDDEN
)
inline fun Parcel.readBoolean(): Boolean = readBooleanCompat()

@Suppress("NOTHING_TO_INLINE")
inline fun Parcel.writeBooleanCompat(value: Boolean) {
    writeInt(if (value) 1 else 0)
}

@Suppress("NOTHING_TO_INLINE")
inline fun Parcel.readBooleanCompat(): Boolean = readInt() != 0

/**
 * Writes `null` as [Float.NaN]. Only uses 4 bytes.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Parcel.writeFloatOrNan(value: Float?) {
    writeFloat(value ?: Float.NaN)
}

/**
 * Reads [Float.NaN] as `null`.
 */
@SuppressLint("ParcelClassLoader")
@Suppress("NOTHING_TO_INLINE")
inline fun Parcel.readFloatOrNull(): Float? = readFloat().takeIf { !it.isNaN() }

/**
 * Writes `null` as [Double.NaN]. Only uses 4 bytes.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Parcel.writeDoubleOrNan(value: Double?) {
    writeDouble(value ?: Double.NaN)
}

/**
 * Reads [Double.NaN] as `null`.
 */
@SuppressLint("ParcelClassLoader")
@Suppress("NOTHING_TO_INLINE")
inline fun Parcel.readDoubleOrNull(): Double? = readDouble().takeIf { !it.isNaN() }

inline fun <reified E> Parcel.readList(cl: ClassLoader? = E::class.java.classLoader): List<E> =
        mutableListOf<E>().apply {
            readList(this as List<*>, cl)
        }

inline fun <reified T> Parcel.readTypedValue(cl: ClassLoader? = T::class.java.classLoader): T =
        readValue(cl) as T

@Deprecated("Use readTypedValue instead.", replaceWith = ReplaceWith(expression = "readTypedValue"))
inline fun <reified T> Parcel.readValueTyped(cl: ClassLoader? = T::class.java.classLoader): T =
        readValue(cl) as T
