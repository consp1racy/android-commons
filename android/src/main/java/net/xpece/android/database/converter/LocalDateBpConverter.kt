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
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import java.sql.Date

/**
 * Converts from a [LocalDate] to a [java.sql.Date] for Java 8.
 */
object LocalDateBpConverter : Converter<LocalDate, Date> {

    override fun getMappedType(): Class<LocalDate> {
        return LocalDate::class.java
    }

    override fun getPersistedType(): Class<Date> {
        return Date::class.java
    }

    override fun getPersistedSize(): Int? {
        return null
    }

    override fun convertToPersisted(value: LocalDate?): Date? {
        if (value == null) {
            return null
        }
        val instant = value.atStartOfDay(ZoneId.systemDefault()).toInstant()
        return Date(instant.toEpochMilli())
    }

    override fun convertToMapped(type: Class<out LocalDate>, value: Date?): LocalDate? {
        if (value == null) {
            return null
        }
        return DateTimeUtils.toLocalDate(value)
    }
}
