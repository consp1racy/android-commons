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
import net.xpece.android.time.toLocalTime
import net.xpece.android.time.toSqlTime
import org.threeten.bp.LocalTime
import java.sql.Time

/**
 * Converts from a [LocalTime] to a [T].
 */
abstract class LocalTimeBpConverter<T> : Converter<LocalTime, T> {

    override fun getMappedType(): Class<LocalTime> {
        return LocalTime::class.java
    }

    override fun getPersistedSize(): Int? {
        return null
    }

    /**
     * Converts from a [LocalTime] to a [String].
     */
    object WithString : LocalTimeBpConverter<String>() {
        override fun getPersistedType(): Class<String> {
            return String::class.java
        }

        override fun convertToPersisted(value: LocalTime?): String? {
            if (value == null) {
                return null
            }
            return value.toString()
        }

        override fun convertToMapped(type: Class<out LocalTime>?, value: String?): LocalTime? {
            if (value == null) {
                return null
            }
            return LocalTime.parse(value)
        }
    }

    /**
     * Converts from a [LocalTime] to a [Time].
     * Safe to use once requery is fixed. requery-rc5 whould be fine.
     */
    object WithTime : LocalTimeBpConverter<Time>() {
        override fun getPersistedType(): Class<Time> {
            return Time::class.java
        }

        override fun convertToPersisted(value: LocalTime?): Time? {
            if (value == null) {
                return null
            }
            return value.toSqlTime()
        }

        override fun convertToMapped(type: Class<out LocalTime>, value: Time?): LocalTime? {
            if (value == null) {
                return null
            }
            return value.toLocalTime()
        }
    }
}
