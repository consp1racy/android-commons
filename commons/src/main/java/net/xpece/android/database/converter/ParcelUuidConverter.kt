package net.xpece.android.database.converter

import android.os.ParcelUuid
import io.requery.Converter
import java.nio.ByteBuffer
import java.util.*

object ParcelUuidConverter : Converter<ParcelUuid, ByteArray> {
    override fun getMappedType(): Class<ParcelUuid> {
        return ParcelUuid::class.java
    }

    override fun getPersistedType(): Class<ByteArray> {
        return ByteArray::class.java
    }

    override fun getPersistedSize(): Int? {
        return 16
    }

    override fun convertToPersisted(value: ParcelUuid?): ByteArray? {
        if (value == null) {
            return null
        }
        val bytes = ByteArray(16)
        val buffer = ByteBuffer.wrap(bytes)
        val uuid = value.uuid
        buffer.putLong(uuid.mostSignificantBits)
        buffer.putLong(uuid.leastSignificantBits)
        return bytes
    }

    override fun convertToMapped(type: Class<out ParcelUuid>, value: ByteArray?): ParcelUuid? {
        if (value == null) {
            return null
        }
        val buffer = ByteBuffer.wrap(value)
        val uuid = UUID(buffer.long, buffer.long)
        return ParcelUuid(uuid)
    }
}
