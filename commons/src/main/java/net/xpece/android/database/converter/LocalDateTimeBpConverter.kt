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
import net.xpece.android.time.toLocalDateTime
import net.xpece.android.time.toSqlTimestamp
import org.threeten.bp.LocalDateTime
import java.sql.Timestamp

/**
 * Converts from a [LocalDateTime] to a [T].
 */
abstract class LocalDateTimeBpConverter<T> : Converter<LocalDateTime, T> {

    override fun getMappedType(): Class<LocalDateTime> {
        return LocalDateTime::class.java
    }

    override fun getPersistedSize(): Int? {
        return null
    }

    /**
     * Converts from a [LocalDateTime] to a [String].
     * Can be compared as long as year is in range of 0 to 9999.
     */
    object WithString : LocalDateTimeBpConverter<String>() {
        override fun getPersistedType(): Class<String> {
            return String::class.java
        }

        override fun convertToPersisted(value: LocalDateTime?): String? {
            if (value == null) {
                return null
            }
            return value.toString()
        }

        override fun convertToMapped(type: Class<out LocalDateTime>?, value: String?): LocalDateTime? {
            if (value == null) {
                return null
            }
            return LocalDateTime.parse(value)
        }
    }

    /**
     * Converts from a [LocalDateTime] to a [Timestamp].
     * Safe to use since requery-1.0.0.
     */
    object WithTimestamp : LocalDateTimeBpConverter<Timestamp>() {
        override fun getPersistedType(): Class<Timestamp> {
            return Timestamp::class.java
        }

        override fun convertToPersisted(value: LocalDateTime?): Timestamp? {
            if (value == null) {
                return null
            }
            return value.toSqlTimestamp()
        }

        override fun convertToMapped(type: Class<out LocalDateTime>,
                                     value: Timestamp?): LocalDateTime? {
            if (value == null) {
                return null
            }
            return value.toLocalDateTime()
        }

    }
}
