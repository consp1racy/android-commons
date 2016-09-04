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
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import java.sql.Time

/**
 * Converts from a [LocalTime] to a [java.sql.Time] for Java 8.
 */
object LocalTimeBpConverter : Converter<LocalTime, Time> {

    override fun getMappedType(): Class<LocalTime> {
        return LocalTime::class.java
    }

    override fun getPersistedType(): Class<Time> {
        return Time::class.java
    }

    override fun getPersistedSize(): Int? {
        return null
    }

    override fun convertToPersisted(value: LocalTime?): Time? {
        if (value == null) {
            return null
        }
        val instant = value.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()
        return Time(instant.toEpochMilli())
    }

    override fun convertToMapped(type: Class<out LocalTime>, value: Time?): LocalTime? {
        if (value == null) {
            return null
        }
        return DateTimeUtils.toLocalTime(value)
    }
}
