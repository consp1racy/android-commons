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
import net.xpece.android.time.toInstantCompat
import net.xpece.android.time.toSqlTimestamp
import org.threeten.bp.Instant
import java.sql.Timestamp

/**
 * Converts from a [Instant] to a [T].
 */
abstract class InstantBpConverter<T> : Converter<Instant, T> {

    override fun getMappedType(): Class<Instant> {
        return Instant::class.java
    }

    override fun getPersistedSize(): Int? {
        return null
    }

    /**
     * Converts from a [Instant] to a [String].
     * Can be compared until Wed Nov 16 5138 09:46:39.
     */
    object WithString : InstantBpConverter<String>() {
        override fun getPersistedType(): Class<String> {
            return String::class.java
        }

        override fun convertToPersisted(value: Instant?): String? {
            if (value == null) {
                return null
            }
            return value.toString()
        }

        override fun convertToMapped(type: Class<out Instant>?, value: String?): Instant? {
            if (value == null) {
                return null
            }
            return Instant.parse(value)
        }
    }

    /**
     * Converts from a [Instant] to a [Timestamp].
     * Safe to use since requery-1.0.0.
     */
    object WithTimestamp : InstantBpConverter<Timestamp>() {
        override fun getPersistedType(): Class<Timestamp> {
            return Timestamp::class.java
        }

        override fun convertToPersisted(value: Instant?): Timestamp? {
            if (value == null) {
                return null
            }
            return value.toSqlTimestamp()
        }

        override fun convertToMapped(type: Class<out Instant>,
                                     value: Timestamp?): Instant? {
            if (value == null) {
                return null
            }
            return value.toInstantCompat()
        }

    }
}
