package net.xpece.android.time

import org.threeten.bp.*
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.util.*

@JvmField
val TIME_ZONE_UTC = TimeZone.getTimeZone("UTC")!!

private val CALENDAR = object : ThreadLocal<Calendar>() {
    override fun initialValue() = Calendar.getInstance(TIME_ZONE_UTC)
}

fun java.util.Date.toLocalDateTime(): LocalDateTime {
    val instant = toInstantCompat()
    return LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
}

fun java.util.Date.toOffsetDateTime(): OffsetDateTime {
    val instant = toInstantCompat()
    return OffsetDateTime.ofInstant(instant, ZoneOffset.UTC)
}

fun java.util.Date.toZonedDateTime(): ZonedDateTime {
    val instant = toInstantCompat()
    return ZonedDateTime.ofInstant(instant, ZoneOffset.UTC)
}

fun java.util.Date.toLocalTime(): LocalTime {
    val instant = toInstantCompat()
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
    return localDateTime.toLocalTime()
}

fun java.util.Date.toLocalDate(): LocalDate {
    val instant = toInstantCompat()
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
    return localDateTime.toLocalDate()
}

fun java.util.Date.toInstantCompat(): Instant = Instant.ofEpochMilli(time)

fun Timestamp.toInstantCompat(): Instant {
    val seconds = time / 1000
    return Instant.ofEpochSecond(seconds, nanos.toLong())
}

fun LocalDateTime.toSqlTimestamp(): Timestamp {
    val cal = CALENDAR.get()!!
    cal.clear()
    cal.set(year, monthValue - 1, dayOfMonth, hour, minute, second)
    val timestamp = Timestamp(cal.time.time)
    timestamp.nanos = nano
    return timestamp
}

fun LocalTime.toSqlTime(): Time {
    val cal = CALENDAR.get()!!
    cal.clear()
    cal.set(1970, 0, 1, hour, minute, second)
    val time = Time(cal.time.time)
    return time
}

fun LocalDate.toSqlDate(): Date {
    val cal = CALENDAR.get()!!
    cal.clear()
    cal.set(year, monthValue - 1, dayOfMonth)
    val date = Date(cal.time.time)
    return date
}

fun Instant.toSqlTimestamp(): Timestamp {
    val local = LocalDateTime.ofInstant(this, ZoneOffset.UTC)
    return local.toSqlTimestamp()
}

fun OffsetDateTime.toSqlTimestamp(): Timestamp {
    val instant = toInstant()
    return instant.toSqlTimestamp()
}

fun ZonedDateTime.toSqlTimestamp(): Timestamp {
    val instant = toInstant()
    return instant.toSqlTimestamp()
}
