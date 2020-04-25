@file:JvmName("XpParcel")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.os

import android.annotation.SuppressLint
import android.os.Parcel

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
@Deprecated(
    "Shadows method since API 29.",
    replaceWith = ReplaceWith("writeBooleanCompat"),
    level = DeprecationLevel.HIDDEN
)
inline fun Parcel.writeBoolean(value: Boolean) = writeBooleanCompat(value)

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
@Deprecated(
    "Shadows method since API 29.",
    replaceWith = ReplaceWith("readBooleanCompat"),
    level = DeprecationLevel.HIDDEN
)
inline fun Parcel.readBoolean(): Boolean = readBooleanCompat()

inline fun Parcel.writeBooleanCompat(value: Boolean) {
    writeInt(if (value) 1 else 0)
}

inline fun Parcel.readBooleanCompat(): Boolean = readInt() != 0

/**
 * Writes `null` as [Float.NaN]. Only uses 4 bytes.
 */
inline fun Parcel.writeFloatOrNan(value: Float?) {
    writeFloat(value ?: Float.NaN)
}

/**
 * Reads [Float.NaN] as `null`.
 */
@SuppressLint("ParcelClassLoader")
inline fun Parcel.readFloatOrNull(): Float? = readFloat().takeUnless(Float::isNaN)

/**
 * Writes `null` as [Double.NaN]. Only uses 8 bytes.
 */
inline fun Parcel.writeDoubleOrNan(value: Double?) {
    writeDouble(value ?: Double.NaN)
}

/**
 * Reads [Double.NaN] as `null`.
 */
@SuppressLint("ParcelClassLoader")
inline fun Parcel.readDoubleOrNull(): Double? = readDouble().takeUnless(Double::isNaN)

inline fun <reified E> Parcel.readList(cl: ClassLoader? = E::class.java.classLoader): List<E> =
    mutableListOf<E>().apply {
        readList(this as List<*>, cl)
    }

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
@Deprecated(
    "Renamed.",
    replaceWith = ReplaceWith("readValue", "net.xpece.android.os.readValue"),
    level = DeprecationLevel.WARNING
)
inline fun <reified T> Parcel.readTypedValue(cl: ClassLoader? = T::class.java.classLoader): T =
    readValue(cl) as T

// Name is same but parameters differ.
@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified T> Parcel.readValue(cl: ClassLoader? = T::class.java.classLoader): T =
    readValue(cl) as T
