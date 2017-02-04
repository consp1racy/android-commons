package net.xpece.android.util

import android.os.Bundle
import android.os.ParcelUuid
import java.util.*

private inline fun UUID?.wrap() = if (this != null) ParcelUuid(this) else null
private inline fun ParcelUuid?.unwrap() = this?.uuid
fun Bundle.putUuid(key: String, uuid: UUID?) = this.putParcelable(key, uuid.wrap())
fun Bundle.getUuid(key: String) = this.getParcelable<ParcelUuid?>(key).unwrap()
