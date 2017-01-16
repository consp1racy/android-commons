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
import net.xpece.android.time.toLocalDate
import net.xpece.android.time.toSqlDate
import org.threeten.bp.LocalDate
import java.sql.Date

/**
 * Converts from a [LocalDate] to a [T].
 */
abstract class LocalDateBpConverter<T> : Converter<LocalDate, T> {

    override fun getMappedType(): Class<LocalDate> {
        return LocalDate::class.java
    }

    override fun getPersistedSize(): Int? {
        return null
    }

    /**
     * Converts from a [LocalDate] to a [String].
     * Can be compared as long as year is in range of 0 to 9999.
     */
    object WithString : LocalDateBpConverter<String>() {
        override fun getPersistedType(): Class<String> {
            return String::class.java
        }

        override fun convertToPersisted(value: LocalDate?): String? {
            if (value == null) {
                return null
            }
            return value.toString()
        }

        override fun convertToMapped(type: Class<out LocalDate>?, value: String?): LocalDate? {
            if (value == null) {
                return null
            }
            return LocalDate.parse(value)
        }
    }

    /**
     * Converts from a [LocalDate] to a [Date].
     * Safe to use since requery-1.0.0.
     */
    object WithDate : LocalDateBpConverter<Date>() {
        override fun getPersistedType(): Class<Date> {
            return Date::class.java
        }

        override fun convertToPersisted(value: LocalDate?): Date? {
            if (value == null) {
                return null
            }
            return value.toSqlDate()
        }

        override fun convertToMapped(type: Class<out LocalDate>, value: Date?): LocalDate? {
            if (value == null) {
                return null
            }
            return value.toLocalDate()
        }
    }
}
