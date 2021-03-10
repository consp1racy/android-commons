package net.xpece.android.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal

object BigDecimalAdapter {
    @ToJson
    fun toJson(number: BigDecimal): String {
        return number.toString()
    }

    @FromJson
    fun fromJson(number: String): BigDecimal {
        return BigDecimal(number)
    }
}
