package net.xpece.android.os

import android.os.Parcel
import net.xpece.android.app.toBoolean
import net.xpece.android.app.toByte

fun Parcel.writeBoolean(value: Boolean?) {
    val byte = value.toByte()
    writeByte(byte)
}

fun Parcel.readBoolean(): Boolean? {
    val byte = readByte()
    return byte.toBoolean()
}

fun <E> Parcel.readList(cl: ClassLoader): List<E> = mutableListOf<E>().apply {
    readList(this, cl)
}
