package net.xpece.android.time

import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDateTime
import java.sql.Timestamp
import java.util.*
import kotlin.test.assertEquals

/**
 * Created by Eugen on 16.01.2017.
 */
class DateTimeUtilsUTCTest {
    private val NEW_YORK = TimeZone.getTimeZone("-05:00")
    private val UTC = TimeZone.getTimeZone("UTC")

    private val MILLIS = 949332600555L

    private lateinit var LOCAL: LocalDateTime
    private lateinit var TIMESTAMP: Timestamp

    private fun createTimestamp() = Timestamp(MILLIS)

    private fun createLocal(): LocalDateTime {
        var out = LocalDateTime.of(2000, 1, 31, 15, 30)
        out = out.withNano(555 * 1000000)
        return out
    }

    @Before
    fun setupDefaults() {
        TimeZone.setDefault(NEW_YORK)

        LOCAL = createLocal()
        TIMESTAMP = createTimestamp()

        TimeZone.setDefault(UTC)
    }

    @Test
    fun testTimestamp() {
        val timestamp = createTimestamp()
        assertEquals(TIMESTAMP.time, timestamp.time, "Timestamp fucked up.")
    }

    @Test
    fun testLocal() {
        val local = createLocal()
        assertEquals(LOCAL, local, "LocalDateTime fucked up.")
    }

    @Test
    fun testToTimestamp() {
        val newTimestamp = LOCAL.toSqlTimestamp()
        assertEquals(TIMESTAMP, newTimestamp, "LocalDateTime.toSqlTimestamp fucked up.")
    }

    @Test
    fun testFromTimestamp() {
        val newLocal = TIMESTAMP.toLocalDateTime()
        assertEquals(LOCAL, newLocal, "Timestamp.toLocalDateTime fucked up.")
    }
}
