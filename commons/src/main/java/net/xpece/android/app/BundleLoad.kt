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
import kotlin.reflect.KMutableProperty0

@JvmName("getBoolean")
inline infix fun Bundle.load(property: KMutableProperty0<Boolean>) {
    property.set(getBoolean(property.name))
}

@JvmName("getByte")
inline infix fun Bundle.load(property: KMutableProperty0<Byte>) {
    property.set(getByte(property.name))
}

@JvmName("getChar")
inline infix fun Bundle.load(property: KMutableProperty0<Char>) {
    property.set(getChar(property.name))
}

@JvmName("getShort")
inline infix fun Bundle.load(property: KMutableProperty0<Short>) {
    property.set(getShort(property.name))
}

@JvmName("getInt")
inline infix fun Bundle.load(property: KMutableProperty0<Int>) {
    property.set(getInt(property.name))
}

@JvmName("getLong")
inline infix fun Bundle.load(property: KMutableProperty0<Long>) {
    property.set(getLong(property.name))
}

@JvmName("getFloat")
inline infix fun Bundle.load(property: KMutableProperty0<Float>) {
    property.set(getFloat(property.name))
}

@JvmName("getDouble")
inline infix fun Bundle.load(property: KMutableProperty0<Double>) {
    property.set(getDouble(property.name))
}

@JvmName("getString")
inline infix fun Bundle.load(property: KMutableProperty0<String>) {
    property.set(getString(property.name))
}

@JvmName("getCharSequence")
inline infix fun Bundle.load(property: KMutableProperty0<CharSequence>) {
    property.set(getCharSequence(property.name))
}

@JvmName("getIntegerArrayList")
inline infix fun Bundle.load(property: KMutableProperty0<ArrayList<Int>>) {
    property.set(getIntegerArrayList(property.name))
}

@JvmName("getStringArrayList")
inline infix fun Bundle.load(property: KMutableProperty0<ArrayList<String>>) {
    property.set(getStringArrayList(property.name))
}

@JvmName("getCharSequenceArrayList")
inline infix fun Bundle.load(property: KMutableProperty0<ArrayList<CharSequence>>) {
    property.set(getCharSequenceArrayList(property.name))
}

@JvmName("getSerializable")
inline infix fun <reified T : Serializable> Bundle.load(property: KMutableProperty0<T>) {
    property.set(getSerializable(property.name) as T)
}

@JvmName("getBooleanArray")
inline infix fun Bundle.load(property: KMutableProperty0<BooleanArray>) {
    property.set(getBooleanArray(property.name))
}

@JvmName("getByteArray")
inline infix fun Bundle.load(property: KMutableProperty0<ByteArray>) {
    property.set(getByteArray(property.name))
}

@JvmName("getCharArray")
inline infix fun Bundle.load(property: KMutableProperty0<CharArray>) {
    property.set(getCharArray(property.name))
}

@JvmName("getShortArray")
inline infix fun Bundle.load(property: KMutableProperty0<ShortArray>) {
    property.set(getShortArray(property.name))
}

@JvmName("getIntArray")
inline infix fun Bundle.load(property: KMutableProperty0<IntArray>) {
    property.set(getIntArray(property.name))
}

@JvmName("getLongArray")
inline infix fun Bundle.load(property: KMutableProperty0<LongArray>) {
    property.set(getLongArray(property.name))
}

@JvmName("getFloatArray")
inline infix fun Bundle.load(property: KMutableProperty0<FloatArray>) {
    property.set(getFloatArray(property.name))
}

@JvmName("getDoubleArray")
inline infix fun Bundle.load(property: KMutableProperty0<DoubleArray>) {
    property.set(getDoubleArray(property.name))
}

@JvmName("getStringArray")
inline infix fun Bundle.load(property: KMutableProperty0<Array<String>>) {
    property.set(getStringArray(property.name))
}

@JvmName("getCharSequenceArray")
inline infix fun Bundle.load(property: KMutableProperty0<Array<CharSequence>>) {
    property.set(getCharSequenceArray(property.name))
}

@JvmName("getParcelable")
inline infix fun <T : Parcelable> Bundle.load(property: KMutableProperty0<T>) {
    property.set(getParcelable(property.name))
}

@RequiresApi(21)
@TargetApi(21)
@JvmName("getSize")
inline infix fun Bundle.load(property: KMutableProperty0<Size>) {
    property.set(getSize(property.name))
}

@RequiresApi(21)
@TargetApi(21)
@JvmName("getSizeF")
inline infix fun Bundle.load(property: KMutableProperty0<SizeF>) {
    property.set(getSizeF(property.name))
}

@JvmName("getParcelableArray")
inline infix fun Bundle.load(property: KMutableProperty0<Array<Parcelable>>) {
    property.set(getParcelableArray(property.name))
}

@JvmName("getParcelableArrayList")
inline infix fun <T : Parcelable> Bundle.load(property: KMutableProperty0<ArrayList<out T>>) {
    property.set(getParcelableArrayList(property.name))
}

@JvmName("getSparseParcelableArray")
inline infix fun <T : Parcelable> Bundle.load(property: KMutableProperty0<SparseArray<out T>>) {
    property.set(getSparseParcelableArray(property.name))
}

@JvmName("getBundle")
inline infix fun Bundle.load(property: KMutableProperty0<Bundle>) {
    property.set(getBundle(property.name))
}

@RequiresApi(18)
@TargetApi(18)
@JvmName("getBinder")
inline infix fun <reified T : IBinder> Bundle.load(property: KMutableProperty0<T>) {
    property.set(getBinder(property.name) as T)
}
