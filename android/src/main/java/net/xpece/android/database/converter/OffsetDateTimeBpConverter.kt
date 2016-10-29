/*
 * Copyright 2016 requery.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.xpece.android.database.converter

import io.requery.Converter
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneId
import java.sql.Timestamp

/**
 * Converts from a [OffsetDateTime] to a [T].
 */
abstract class OffsetDateTimeBpConverter<T> : Converter<OffsetDateTime, T> {

    override fun getMappedType(): Class<OffsetDateTime> {
        return OffsetDateTime::class.java
    }

    override fun getPersistedSize(): Int? {
        return null
    }

    /**
     * Converts from a [OffsetDateTime] to a [String].
     * Can be compared as long as year is in range of 0 to 9999.
     * Strips zone ID.
     */
    object WithString : OffsetDateTimeBpConverter<String>() {
        override fun getPersistedType(): Class<String> {
            return String::class.java
        }

        override fun convertToPersisted(value: OffsetDateTime?): String? {
            if (value == null) {
                return null
            }
            return value.toInstant().toString().padStart(14, '0')
        }

        override fun convertToMapped(type: Class<out OffsetDateTime>?, value: String?): OffsetDateTime? {
            if (value == null) {
                return null
            }
            return OffsetDateTime.ofInstant(Instant.parse(value.trimStart('0')), ZoneId.systemDefault())
        }
    }

    /**
     * Converts from a [OffsetDateTime] to a [Timestamp].
     * Safe to use since requery-1.0.0.
     * Strips zone ID.
     */
    object WithTimestamp : OffsetDateTimeBpConverter<Timestamp>() {
        override fun getPersistedType(): Class<Timestamp> {
            return Timestamp::class.java
        }

        override fun convertToPersisted(value: OffsetDateTime?): Timestamp? {
            if (value == null) {
                return null
            }
            val instant = value.toInstant()
            return DateTimeUtils.toSqlTimestamp(instant)
        }

        override fun convertToMapped(type: Class<out OffsetDateTime>,
                                     value: Timestamp?): OffsetDateTime? {
            if (value == null) {
                return null
            }
            return OffsetDateTime.ofInstant(DateTimeUtils.toInstant(value), ZoneId.systemDefault())
        }
    }
}
