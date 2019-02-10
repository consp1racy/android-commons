@file:JvmName("XpBundle")

package net.xpece.android.os

import android.os.Bundle
import android.os.ParcelUuid
import java.util.*

private inline fun UUID?.wrap() = if (this != null) ParcelUuid(this) else null
private inline fun ParcelUuid?.unwrap() = this?.uuid
fun Bundle.putUuid(key: String, uuid: UUID?) = this.putParcelable(key, uuid.wrap())
fun Bundle.getUuid(key: String) = this.getParcelable<ParcelUuid?>(key).unwrap()

@JvmName("putIntegerMap")
fun Bundle.writeMap(map: Map<String, Int>) {
    map.forEach {
        putInt(it.key, it.value)
    }
}

fun Bundle.readIntegerMap(): Map<String, Int> {
    val out = mutableMapOf<String, Int>()
    val keys = keySet()
    keys.forEach { key ->
        val value = getInt(key)
        out.put(key, value)
    }
    return out
}
