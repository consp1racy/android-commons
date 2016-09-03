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
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;

import io.requery.Converter;

/**
 * Converts from a {@link OffsetDateTime} to a {@link java.sql.Timestamp} for Java 8.
 */
public class OffsetDateTimeBpConverter implements Converter<OffsetDateTime, java.sql.Timestamp> {

    @Override
    public Class<OffsetDateTime> getMappedType() {
        return OffsetDateTime.class;
    }

    @Override
    public Class<java.sql.Timestamp> getPersistedType() {
        return java.sql.Timestamp.class;
    }

    @Override
    public Integer getPersistedSize() {
        return null;
    }

    @Override
    public java.sql.Timestamp convertToPersisted(OffsetDateTime value) {
        if (value == null) {
            return null;
        }
        Instant instant = value.toInstant();
        return DateTimeUtils.toSqlTimestamp(instant);
    }

    @Override
    public OffsetDateTime convertToMapped(Class<? extends OffsetDateTime> type,
                                          java.sql.Timestamp value) {
        if (value == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(DateTimeUtils.toInstant(value), ZoneId.systemDefault());
    }
}
