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
fun Bundle.put(property: KProperty0<Boolean>) {
    putBoolean(property.name, property.get())
}

@JvmName("putByte")
fun Bundle.put(property: KProperty0<Byte>) {
    putByte(property.name, property.get())
}

@JvmName("putChar")
fun Bundle.put(property: KProperty0<Char>) {
    putChar(property.name, property.get())
}

@JvmName("putShort")
fun Bundle.put(property: KProperty0<Short>) {
    putShort(property.name, property.get())
}

@JvmName("putInt")
fun Bundle.put(property: KProperty0<Int>) {
    putInt(property.name, property.get())
}

@JvmName("putLong")
fun Bundle.put(property: KProperty0<Long>) {
    putLong(property.name, property.get())
}

@JvmName("putFloat")
fun Bundle.put(property: KProperty0<Float>) {
    putFloat(property.name, property.get())
}

@JvmName("putDouble")
fun Bundle.put(property: KProperty0<Double>) {
    putDouble(property.name, property.get())
}

@JvmName("putString")
fun Bundle.put(property: KProperty0<String>) {
    putString(property.name, property.get())
}

@JvmName("putCharSequence")
fun Bundle.put(property: KProperty0<CharSequence>) {
    putCharSequence(property.name, property.get())
}

@JvmName("putIntegerArrayList")
fun Bundle.put(property: KProperty0<ArrayList<Int>>) {
    putIntegerArrayList(property.name, property.get())
}

@JvmName("putStringArrayList")
fun Bundle.put(property: KProperty0<ArrayList<String>>) {
    putStringArrayList(property.name, property.get())
}

@JvmName("putCharSequenceArrayList")
fun Bundle.put(property: KProperty0<ArrayList<CharSequence>>) {
    putCharSequenceArrayList(property.name, property.get())
}

@JvmName("putSerializable")
fun Bundle.put(property: KProperty0<Serializable>) {
    putSerializable(property.name, property.get())
}

@JvmName("putBooleanArray")
fun Bundle.put(property: KProperty0<BooleanArray>) {
    putBooleanArray(property.name, property.get())
}

@JvmName("putByteArray")
fun Bundle.put(property: KProperty0<ByteArray>) {
    putByteArray(property.name, property.get())
}

@JvmName("putCharArray")
fun Bundle.put(property: KProperty0<CharArray>) {
    putCharArray(property.name, property.get())
}

@JvmName("putShortArray")
fun Bundle.put(property: KProperty0<ShortArray>) {
    putShortArray(property.name, property.get())
}

@JvmName("putIntArray")
fun Bundle.put(property: KProperty0<IntArray>) {
    putIntArray(property.name, property.get())
}

@JvmName("putLongArray")
fun Bundle.put(property: KProperty0<LongArray>) {
    putLongArray(property.name, property.get())
}

@JvmName("putFloatArray")
fun Bundle.put(property: KProperty0<FloatArray>) {
    putFloatArray(property.name, property.get())
}

@JvmName("putDoubleArray")
fun Bundle.put(property: KProperty0<DoubleArray>) {
    putDoubleArray(property.name, property.get())
}

@JvmName("putStringArray")
fun Bundle.put(property: KProperty0<Array<String>>) {
    putStringArray(property.name, property.get())
}

@JvmName("putCharSequenceArray")
fun Bundle.put(property: KProperty0<Array<CharSequence>>) {
    putCharSequenceArray(property.name, property.get())
}

/// Bundle

@JvmName("putParcelable")
fun <T : Parcelable> Bundle.put(property: KProperty0<T>) {
    putParcelable(property.name, property.get())
}

@RequiresApi(21)
@TargetApi(21)
@JvmName("putSize")
fun Bundle.put(property: KProperty0<Size>) {
    putSize(property.name, property.get())
}

@RequiresApi(21)
@TargetApi(21)
@JvmName("putSizeF")
fun Bundle.put(property: KProperty0<SizeF>) {
    putSizeF(property.name, property.get())
}

@JvmName("putParcelableArray")
fun <T : Parcelable> Bundle.put(property: KProperty0<Array<out T>>) {
    putParcelableArray(property.name, property.get())
}

@JvmName("putParcelableArrayList")
fun <T : Parcelable> Bundle.put(property: KProperty0<ArrayList<out T>>) {
    putParcelableArrayList(property.name, property.get())
}

@JvmName("putSparseParcelableArray")
fun <T : Parcelable> Bundle.put(property: KProperty0<SparseArray<out T>>) {
    putSparseParcelableArray(property.name, property.get())
}

@JvmName("putBundle")
fun Bundle.put(property: KProperty0<Bundle>) {
    putBundle(property.name, property.get())
}

@RequiresApi(18)
@TargetApi(18)
@JvmName("putBinder")
fun Bundle.put(property: KProperty0<IBinder>) {
    putBinder(property.name, property.get())
}
