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
import net.xpece.android.time.toSqlTimestamp
import net.xpece.android.time.toZonedDateTime
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import java.sql.Timestamp

/**
 * Converts from a [ZonedDateTime] to a [T].
 */
abstract class ZonedDateTimeBpConverter<T> : Converter<ZonedDateTime, T> {

    override fun getMappedType(): Class<ZonedDateTime> {
        return ZonedDateTime::class.java
    }

    override fun getPersistedSize(): Int? {
        return null
    }

    /**
     * Converts from a [ZonedDateTime] to a [String].
     * Can be compared as long as year is in range of 0 to 9999.
     * Strips zone ID.
     */
    object WithString : ZonedDateTimeBpConverter<String>() {
        override fun getPersistedType(): Class<String> {
            return String::class.java
        }

        override fun convertToPersisted(value: ZonedDateTime?): String? {
            if (value == null) {
                return null
            }
            return value.withZoneSameInstant(ZoneOffset.UTC).toInstant().toString()
        }

        override fun convertToMapped(type: Class<out ZonedDateTime>?, value: String?): ZonedDateTime? {
            if (value == null) {
                return null
            }
            val instant = Instant.parse(value)
            return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC)
        }
    }

    /**
     * Converts from a [ZonedDateTime] to a [Timestamp].
     * Safe to use since requery-1.0.0.
     * Strips zone ID.
     */
    object WithTimestamp : ZonedDateTimeBpConverter<Timestamp>() {
        override fun getPersistedType(): Class<Timestamp> {
            return Timestamp::class.java
        }

        override fun convertToPersisted(value: ZonedDateTime?): Timestamp? {
            if (value == null) {
                return null
            }
            return value.toSqlTimestamp()
        }

        override fun convertToMapped(type: Class<out ZonedDateTime>,
                                     value: Timestamp?): ZonedDateTime? {
            if (value == null) {
                return null
            }
            return value.toZonedDateTime()
        }
    }
}
