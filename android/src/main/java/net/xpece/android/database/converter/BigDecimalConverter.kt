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
import java.math.BigDecimal

/**
 * Converts from a [BigDecimal] to a [String].
 */
object BigDecimalConverter : Converter<BigDecimal, String> {

    override fun getMappedType(): Class<BigDecimal> {
        return BigDecimal::class.java
    }

    override fun getPersistedType(): Class<String> {
        return String::class.java
    }

    override fun getPersistedSize(): Int? {
        return null
    }

    override fun convertToPersisted(value: BigDecimal?): String? {
        if (value == null) {
            return null
        }
        return value.toPlainString()
    }

    override fun convertToMapped(type: Class<out BigDecimal>, value: String?): BigDecimal? {
        if (value == null) {
            return null
        }
        return BigDecimal(value)
    }
}
