@file:JvmMultifileClass
@file:JvmName("XpBundle")

package net.xpece.android.app

import android.annotation.TargetApi
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.support.annotation.RequiresApi
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import java.io.Serializable
import java.util.*
import kotlin.reflect.KProperty0

/// BaseBundle

@JvmName("putBoolean")
inline fun Bundle.put(property: KProperty0<Boolean>) {
    putBoolean(property.name, property.get())
}

@JvmName("putByte")
inline fun Bundle.put(property: KProperty0<Byte>) {
    putByte(property.name, property.get())
}

@JvmName("putChar")
inline fun Bundle.put(property: KProperty0<Char>) {
    putChar(property.name, property.get())
}

@JvmName("putShort")
inline fun Bundle.put(property: KProperty0<Short>) {
    putShort(property.name, property.get())
}

@JvmName("putInt")
inline fun Bundle.put(property: KProperty0<Int>) {
    putInt(property.name, property.get())
}

@JvmName("putLong")
inline fun Bundle.put(property: KProperty0<Long>) {
    putLong(property.name, property.get())
}

@JvmName("putFloat")
inline fun Bundle.put(property: KProperty0<Float>) {
    putFloat(property.name, property.get())
}

@JvmName("putDouble")
inline fun Bundle.put(property: KProperty0<Double>) {
    putDouble(property.name, property.get())
}

@JvmName("putString")
inline fun Bundle.put(property: KProperty0<String>) {
    putString(property.name, property.get())
}

@JvmName("putCharSequence")
inline fun Bundle.put(property: KProperty0<CharSequence>) {
    putCharSequence(property.name, property.get())
}

@JvmName("putIntegerArrayList")
inline fun Bundle.put(property: KProperty0<ArrayList<Int>>) {
    putIntegerArrayList(property.name, property.get())
}

@JvmName("putStringArrayList")
inline fun Bundle.put(property: KProperty0<ArrayList<String>>) {
    putStringArrayList(property.name, property.get())
}

@JvmName("putCharSequenceArrayList")
inline fun Bundle.put(property: KProperty0<ArrayList<CharSequence>>) {
    putCharSequenceArrayList(property.name, property.get())
}

@JvmName("putSerializable")
inline fun Bundle.put(property: KProperty0<Serializable>) {
    putSerializable(property.name, property.get())
}

@JvmName("putBooleanArray")
inline fun Bundle.put(property: KProperty0<BooleanArray>) {
    putBooleanArray(property.name, property.get())
}

@JvmName("putByteArray")
inline fun Bundle.put(property: KProperty0<ByteArray>) {
    putByteArray(property.name, property.get())
}

@JvmName("putCharArray")
inline fun Bundle.put(property: KProperty0<CharArray>) {
    putCharArray(property.name, property.get())
}

@JvmName("putShortArray")
inline fun Bundle.put(property: KProperty0<ShortArray>) {
    putShortArray(property.name, property.get())
}

@JvmName("putIntArray")
inline fun Bundle.put(property: KProperty0<IntArray>) {
    putIntArray(property.name, property.get())
}

@JvmName("putLongArray")
inline fun Bundle.put(property: KProperty0<LongArray>) {
    putLongArray(property.name, property.get())
}

@JvmName("putFloatArray")
inline fun Bundle.put(property: KProperty0<FloatArray>) {
    putFloatArray(property.name, property.get())
}

@JvmName("putDoubleArray")
inline fun Bundle.put(property: KProperty0<DoubleArray>) {
    putDoubleArray(property.name, property.get())
}

@JvmName("putStringArray")
inline fun Bundle.put(property: KProperty0<Array<String>>) {
    putStringArray(property.name, property.get())
}

@JvmName("putCharSequenceArray")
inline fun Bundle.put(property: KProperty0<Array<CharSequence>>) {
    putCharSequenceArray(property.name, property.get())
}

/// Bundle

@JvmName("putParcelable")
inline fun <T : Parcelable> Bundle.put(property: KProperty0<T>) {
    putParcelable(property.name, property.get())
}

@RequiresApi(21)
@TargetApi(21)
@JvmName("putSize")
inline fun Bundle.put(property: KProperty0<Size>) {
    putSize(property.name, property.get())
}

@RequiresApi(21)
@TargetApi(21)
@JvmName("putSizeF")
inline fun Bundle.put(property: KProperty0<SizeF>) {
    putSizeF(property.name, property.get())
}

@JvmName("putParcelableArray")
inline fun <T : Parcelable> Bundle.put(property: KProperty0<Array<out T>>) {
    putParcelableArray(property.name, property.get())
}

@JvmName("putParcelableArrayList")
inline fun <T : Parcelable> Bundle.put(property: KProperty0<ArrayList<out T>>) {
    putParcelableArrayList(property.name, property.get())
}

@JvmName("putSparseParcelableArray")
inline fun <T : Parcelable> Bundle.put(property: KProperty0<SparseArray<out T>>) {
    putSparseParcelableArray(property.name, property.get())
}

@JvmName("putBundle")
inline fun Bundle.put(property: KProperty0<Bundle>) {
    putBundle(property.name, property.get())
}

@RequiresApi(18)
@TargetApi(18)
@JvmName("putBinder")
inline fun Bundle.put(property: KProperty0<IBinder>) {
    putBinder(property.name, property.get())
}
