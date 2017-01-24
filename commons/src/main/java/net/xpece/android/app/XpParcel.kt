package net.xpece.android.app

import android.os.Parcel

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
