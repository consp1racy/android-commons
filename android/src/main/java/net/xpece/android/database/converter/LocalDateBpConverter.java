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

package net.xpece.android.database.converter;

import org.threeten.bp.DateTimeUtils;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import io.requery.Converter;

/**
 * Converts from a {@link LocalDate} to a {@link java.sql.Date} for Java 8.
 */
public class LocalDateBpConverter implements Converter<LocalDate, java.sql.Date> {

    @Override
    public Class<LocalDate> getMappedType() {
        return LocalDate.class;
    }

    @Override
    public Class<java.sql.Date> getPersistedType() {
        return java.sql.Date.class;
    }

    @Override
    public Integer getPersistedSize() {
        return null;
    }

    @Override
    public java.sql.Date convertToPersisted(LocalDate value) {
        if (value == null) {
            return null;
        }
        Instant instant = value.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return new java.sql.Date(instant.toEpochMilli());
    }

    @Override
    public LocalDate convertToMapped(Class<? extends LocalDate> type, java.sql.Date value) {
        if (value == null) {
            return null;
        }
        return DateTimeUtils.toLocalDate(value);
    }
}
