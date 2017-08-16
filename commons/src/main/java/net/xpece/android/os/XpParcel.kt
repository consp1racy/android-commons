package net.xpece.android.os

import android.annotation.SuppressLint
import android.os.Parcel

@Suppress("NOTHING_TO_INLINE")
inline fun Parcel.writeBoolean(value: Boolean) {
    writeByte(if (value) 1 else 0)
}

@Suppress("NOTHING_TO_INLINE")
inline fun Parcel.readBoolean(): Boolean = readByte() != 0.toByte()

/**
 * Writes `null` as [Float.NaN]. Only uses 4 bytes.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Parcel.writeFloatNan(value: Float?) {
    writeFloat(value ?: Float.NaN)
}

/**
 * Reads [Float.NaN] as `null`.
 */
@SuppressLint("ParcelClassLoader")
@Suppress("NOTHING_TO_INLINE")
inline fun Parcel.readFloatNan(): Float? = readFloat().takeIf { it != Float.NaN }

/**
 * Writes `null` as [Double.NaN]. Only uses 4 bytes.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Parcel.writeDoubleNan(value: Double?) {
    writeDouble(value ?: Double.NaN)
}

/**
 * Reads [Double.NaN] as `null`.
 */
@SuppressLint("ParcelClassLoader")
@Suppress("NOTHING_TO_INLINE")
inline fun Parcel.readDoubleNan(): Double? = readDouble().takeIf { it != Double.NaN }

inline fun <reified E> Parcel.readList(cl: ClassLoader? = E::class.java.classLoader): List<E> =
        mutableListOf<E>().apply {
            readList(this, cl)
        }

inline fun <reified T> Parcel.readValueTyped(cl: ClassLoader? = T::class.java.classLoader): T =
        readValue(cl) as T
