@file:JvmName("XpBundle")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.os

import android.os.Bundle
import android.os.Parcel
import android.os.ParcelUuid
import android.os.Parcelable
import java.util.*

private inline fun UUID?.wrap() = if (this != null) ParcelUuid(this) else null
private inline fun ParcelUuid?.unwrap() = this?.uuid

fun Bundle.putUuid(key: String, uuid: UUID?) = this.putParcelable(key, uuid.wrap())
fun Bundle.getUuid(key: String) = this.getParcelable<ParcelUuid?>(key).unwrap()

@JvmName("putIntegerMap")
internal fun Bundle.putMap(key: String, map: Map<String, Int>) {
    putParcelable(key, ParcelableStringIntMap(map))
}

internal fun Bundle.getIntegerMap(key: String): Map<String, Int> {
    return getParcelable<ParcelableStringIntMap>(key)!!.map
}

private class ParcelableStringIntMap internal constructor(
        internal val map: Map<String, Int>
) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(map.size)
        map.forEach { (k, v) ->
            dest.writeString(k)
            dest.writeInt(v)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ParcelableStringIntMap> {

        override fun createFromParcel(source: Parcel): ParcelableStringIntMap {
            val size = source.readInt()
            val map = mutableMapOf<String, Int>()
            for (i in 0 until size) {
                val key = source.readString()!!
                val value = source.readInt()
                map[key] = value
            }
            return ParcelableStringIntMap(map)
        }

        override fun newArray(size: Int): Array<ParcelableStringIntMap?> = arrayOfNulls(size)
    }
}
