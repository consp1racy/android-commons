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
import kotlin.reflect.KMutableProperty0

/// BaseBundle

@JvmName("getBoolean")
fun KMutableProperty0<Boolean>.get(bundle: Bundle) {
    set(bundle.getBoolean(name))
}

@JvmName("getByte")
fun KMutableProperty0<Byte>.get(bundle: Bundle) {
    set(bundle.getByte(name))
}

@JvmName("getChar")
fun KMutableProperty0<Char>.get(bundle: Bundle) {
    set(bundle.getChar(name))
}

@JvmName("getShort")
fun KMutableProperty0<Short>.get(bundle: Bundle) {
    set(bundle.getShort(name))
}

@JvmName("getInt")
fun KMutableProperty0<Int>.get(bundle: Bundle) {
    set(bundle.getInt(name))
}

@JvmName("getLong")
fun KMutableProperty0<Long>.get(bundle: Bundle) {
    set(bundle.getLong(name))
}

@JvmName("getFloat")
fun KMutableProperty0<Float>.get(bundle: Bundle) {
    set(bundle.getFloat(name))
}

@JvmName("getDouble")
fun KMutableProperty0<Double>.get(bundle: Bundle) {
    set(bundle.getDouble(name))
}

@JvmName("getString")
fun KMutableProperty0<String>.get(bundle: Bundle) {
    set(bundle.getString(name))
}

@JvmName("getCharSequence")
fun KMutableProperty0<CharSequence>.get(bundle: Bundle) {
    set(bundle.getCharSequence(name))
}

@JvmName("getIntegerArrayList")
fun KMutableProperty0<ArrayList<Int>>.get(bundle: Bundle) {
    set(bundle.getIntegerArrayList(name))
}

@JvmName("getStringArrayList")
fun KMutableProperty0<ArrayList<String>>.get(bundle: Bundle) {
    set(bundle.getStringArrayList(name))
}

@JvmName("getCharSequenceArrayList")
fun KMutableProperty0<ArrayList<CharSequence>>.get(bundle: Bundle) {
    set(bundle.getCharSequenceArrayList(name))
}

@JvmName("getSerializable")
fun KMutableProperty0<Serializable>.get(bundle: Bundle) {
    set(bundle.getSerializable(name))
}

@JvmName("getBooleanArray")
fun KMutableProperty0<BooleanArray>.get(bundle: Bundle) {
    set(bundle.getBooleanArray(name))
}

@JvmName("getByteArray")
fun KMutableProperty0<ByteArray>.get(bundle: Bundle) {
    set(bundle.getByteArray(name))
}

@JvmName("getCharArray")
fun KMutableProperty0<CharArray>.get(bundle: Bundle) {
    set(bundle.getCharArray(name))
}

@JvmName("getShortArray")
fun KMutableProperty0<ShortArray>.get(bundle: Bundle) {
    set(bundle.getShortArray(name))
}

@JvmName("getIntArray")
fun KMutableProperty0<IntArray>.get(bundle: Bundle) {
    set(bundle.getIntArray(name))
}

@JvmName("getLongArray")
fun KMutableProperty0<LongArray>.get(bundle: Bundle) {
    set(bundle.getLongArray(name))
}

@JvmName("getFloatArray")
fun KMutableProperty0<FloatArray>.get(bundle: Bundle) {
    set(bundle.getFloatArray(name))
}

@JvmName("getDoubleArray")
fun KMutableProperty0<DoubleArray>.get(bundle: Bundle) {
    set(bundle.getDoubleArray(name))
}

@JvmName("getStringArray")
fun KMutableProperty0<Array<String>>.get(bundle: Bundle) {
    set(bundle.getStringArray(name))
}

@JvmName("getCharSequenceArray")
fun KMutableProperty0<Array<CharSequence>>.get(bundle: Bundle) {
    set(bundle.getCharSequenceArray(name))
}

/// Bundle

@JvmName("getParcelable")
fun <T : Parcelable> KMutableProperty0<T>.get(bundle: Bundle) {
    set(bundle.getParcelable(name))
}

@RequiresApi(21)
@TargetApi(21)
@JvmName("getSize")
fun KMutableProperty0<Size>.get(bundle: Bundle) {
    set(bundle.getSize(name))
}

@RequiresApi(21)
@TargetApi(21)
@JvmName("getSizeF")
fun KMutableProperty0<SizeF>.get(bundle: Bundle) {
    set(bundle.getSizeF(name))
}

@JvmName("getParcelableArray")
fun KMutableProperty0<Array<out Parcelable>>.get(bundle : Bundle) {
    set(bundle.getParcelableArray(name))
}

@JvmName("getParcelableArrayList")
fun <T : Parcelable> KMutableProperty0<ArrayList<out T>>.get(bundle: Bundle) {
    set(bundle.getParcelableArrayList(name))
}

@JvmName("getSparseParcelableArray")
fun <T : Parcelable> KMutableProperty0<SparseArray<out T>>.get(bundle: Bundle) {
    set(bundle.getSparseParcelableArray(name))
}

@JvmName("getBundle")
fun KMutableProperty0<Bundle>.get(bundle: Bundle) {
    set(bundle.getBundle(name))
}

@RequiresApi(18)
@TargetApi(18)
@JvmName("getBinder")
fun KMutableProperty0<IBinder>.get(bundle: Bundle) {
    set(bundle.getBinder(name))
}
