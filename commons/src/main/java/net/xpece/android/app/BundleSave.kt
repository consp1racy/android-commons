@file:JvmMultifileClass
@file:JvmName("XpBundle")
@file:Suppress("NOTHING_TO_INLINE", "UNUSED")

package net.xpece.android.app

import android.annotation.TargetApi
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import androidx.annotation.RequiresApi
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import java.io.Serializable
import java.util.*
import kotlin.reflect.KProperty0

/// BaseBundle

@JvmName("putBoolean")
inline infix fun Bundle.save(property: KProperty0<Boolean>) {
    putBoolean(property.name, property.get())
}

@JvmName("putByte")
inline infix fun Bundle.save(property: KProperty0<Byte>) {
    putByte(property.name, property.get())
}

@JvmName("putChar")
inline infix fun Bundle.save(property: KProperty0<Char>) {
    putChar(property.name, property.get())
}

@JvmName("putShort")
inline infix fun Bundle.save(property: KProperty0<Short>) {
    putShort(property.name, property.get())
}

@JvmName("putInt")
inline infix fun Bundle.save(property: KProperty0<Int>) {
    putInt(property.name, property.get())
}

@JvmName("putLong")
inline infix fun Bundle.save(property: KProperty0<Long>) {
    putLong(property.name, property.get())
}

@JvmName("putFloat")
inline infix fun Bundle.save(property: KProperty0<Float>) {
    putFloat(property.name, property.get())
}

@JvmName("putDouble")
inline infix fun Bundle.save(property: KProperty0<Double>) {
    putDouble(property.name, property.get())
}

@JvmName("putString")
inline infix fun Bundle.save(property: KProperty0<String>) {
    putString(property.name, property.get())
}

@JvmName("putCharSequence")
inline infix fun Bundle.save(property: KProperty0<CharSequence>) {
    putCharSequence(property.name, property.get())
}

@JvmName("putIntegerArrayList")
inline infix fun Bundle.save(property: KProperty0<ArrayList<Int>>) {
    putIntegerArrayList(property.name, property.get())
}

@JvmName("putStringArrayList")
inline infix fun Bundle.save(property: KProperty0<ArrayList<String>>) {
    putStringArrayList(property.name, property.get())
}

@JvmName("putCharSequenceArrayList")
inline infix fun Bundle.save(property: KProperty0<ArrayList<CharSequence>>) {
    putCharSequenceArrayList(property.name, property.get())
}

@JvmName("putSerializable")
inline infix fun <T : Serializable> Bundle.save(property: KProperty0<T>) {
    putSerializable(property.name, property.get())
}

@JvmName("putBooleanArray")
inline infix fun Bundle.save(property: KProperty0<BooleanArray>) {
    putBooleanArray(property.name, property.get())
}

@JvmName("putByteArray")
inline infix fun Bundle.save(property: KProperty0<ByteArray>) {
    putByteArray(property.name, property.get())
}

@JvmName("putCharArray")
inline infix fun Bundle.save(property: KProperty0<CharArray>) {
    putCharArray(property.name, property.get())
}

@JvmName("putShortArray")
inline infix fun Bundle.save(property: KProperty0<ShortArray>) {
    putShortArray(property.name, property.get())
}

@JvmName("putIntArray")
inline infix fun Bundle.save(property: KProperty0<IntArray>) {
    putIntArray(property.name, property.get())
}

@JvmName("putLongArray")
inline infix fun Bundle.save(property: KProperty0<LongArray>) {
    putLongArray(property.name, property.get())
}

@JvmName("putFloatArray")
inline infix fun Bundle.save(property: KProperty0<FloatArray>) {
    putFloatArray(property.name, property.get())
}

@JvmName("putDoubleArray")
inline infix fun Bundle.save(property: KProperty0<DoubleArray>) {
    putDoubleArray(property.name, property.get())
}

@JvmName("putStringArray")
inline infix fun Bundle.save(property: KProperty0<Array<String>>) {
    putStringArray(property.name, property.get())
}

@JvmName("putCharSequenceArray")
inline infix fun Bundle.save(property: KProperty0<Array<CharSequence>>) {
    putCharSequenceArray(property.name, property.get())
}

/// Bundle

@JvmName("putParcelable")
inline infix fun <T : Parcelable> Bundle.save(property: KProperty0<T>) {
    putParcelable(property.name, property.get())
}

@RequiresApi(21)
@TargetApi(21)
@JvmName("putSize")
inline infix fun Bundle.save(property: KProperty0<Size>) {
    putSize(property.name, property.get())
}

@RequiresApi(21)
@TargetApi(21)
@JvmName("putSizeF")
inline infix fun Bundle.save(property: KProperty0<SizeF>) {
    putSizeF(property.name, property.get())
}

@JvmName("putParcelableArray")
inline infix fun Bundle.save(property: KProperty0<Array<Parcelable>>) {
    putParcelableArray(property.name, property.get())
}

@JvmName("putParcelableArrayList")
inline infix fun <T : Parcelable> Bundle.save(property: KProperty0<ArrayList<out T>>) {
    putParcelableArrayList(property.name, property.get())
}

@JvmName("putSparseParcelableArray")
inline infix fun <T : Parcelable> Bundle.save(property: KProperty0<SparseArray<out T>>) {
    putSparseParcelableArray(property.name, property.get())
}

@JvmName("putBundle")
inline infix fun Bundle.save(property: KProperty0<Bundle>) {
    putBundle(property.name, property.get())
}

@RequiresApi(18)
@TargetApi(18)
@JvmName("putBinder")
inline infix fun <T : IBinder> Bundle.save(property: KProperty0<T>) {
    putBinder(property.name, property.get())
}
