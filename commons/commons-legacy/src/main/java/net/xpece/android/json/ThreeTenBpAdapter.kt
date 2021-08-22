package net.xpece.android.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.*

object ThreeTenBpAdapter {
    @ToJson fun toJson(dt: Instant): String {
        return dt.toString()
    }

    @ToJson fun toJson(dt: OffsetDateTime): String {
        return dt.toString()
    }

    @ToJson fun toJson(dt: LocalDateTime): String {
        return dt.toString()
    }

    @ToJson fun toJson(dt: LocalDate): String {
        return dt.toString()
    }

    @ToJson fun toJson(dt: LocalTime): String {
        return dt.toString()
    }

    @FromJson fun localTimeFromJson(dt: String): LocalTime {
        return LocalTime.parse(dt)
    }

    @FromJson fun localDateFromJson(dt: String): LocalDate {
        return LocalDate.parse(dt)
    }

    @FromJson fun localDateTimeFromJson(dt: String): LocalDateTime {
        return LocalDateTime.parse(dt)
    }

    @FromJson fun offsetDateTimeFromJson(dt: String): OffsetDateTime {
        return OffsetDateTime.parse(dt)
    }

    @FromJson fun instantFromJson(dt: String): Instant {
        return OffsetDateTime.parse(dt).toInstant() // Preserve zone internally.
    }
}
