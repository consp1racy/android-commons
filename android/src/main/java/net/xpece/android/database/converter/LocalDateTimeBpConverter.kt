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
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.sql.Timestamp

/**
 * Converts from a [LocalDateTime] to a [java.sql.Timestamp] for Java 8.
 */
object LocalDateTimeBpConverter : Converter<LocalDateTime, Timestamp> {

    override fun getMappedType(): Class<LocalDateTime> {
        return LocalDateTime::class.java
    }

    override fun getPersistedType(): Class<Timestamp> {
        return Timestamp::class.java
    }

    override fun getPersistedSize(): Int? {
        return null
    }

    override fun convertToPersisted(value: LocalDateTime?): Timestamp? {
        if (value == null) {
            return null
        }
        val instant = value.atZone(ZoneId.systemDefault()).toInstant()
        return DateTimeUtils.toSqlTimestamp(instant)
    }

    override fun convertToMapped(type: Class<out LocalDateTime>,
                                 value: Timestamp?): LocalDateTime? {
        if (value == null) {
            return null
        }
        return DateTimeUtils.toLocalDateTime(value)
    }
}
