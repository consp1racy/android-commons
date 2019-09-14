@file:JvmName("XpDateTime")
@file:Suppress("NOTHING_TO_INLINE")

package net.xpece.android.text

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


val FORMATTER_DATE_MACHINE = SimpleDateFormat("yyyy-MM-dd", Locale.US)
val FORMATTER_ISO8601 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)

private val FORMATTER_DATE_TIME_HUMAN_MAP : MutableMap<Locale, DateFormat> by lazy { mutableMapOf<Locale, DateFormat>() }
private val FORMATTER_DATE_HUMAN_MAP : MutableMap<Locale, DateFormat> by lazy { mutableMapOf<Locale, DateFormat>() }
private val FORMATTER_TIME_HUMAN_MAP : MutableMap<Locale, DateFormat> by lazy { mutableMapOf<Locale, DateFormat>() }

fun getFormatterDateTimeHuman(locale: Locale): DateFormat {
    var f = FORMATTER_DATE_TIME_HUMAN_MAP[locale]
    if (f == null) {
        f = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MEDIUM, SimpleDateFormat.DEFAULT, locale)
        FORMATTER_DATE_TIME_HUMAN_MAP.put(locale, f)
    }
    return f!!
}

fun getFormatterDateHuman(locale: Locale): DateFormat {
    var f = FORMATTER_DATE_HUMAN_MAP[locale]
    if (f == null) {
        f = SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM, locale)
        FORMATTER_DATE_HUMAN_MAP.put(locale, f)
    }
    return f!!
}

fun getFormatterTimeHuman(locale: Locale): DateFormat {
    var f = FORMATTER_TIME_HUMAN_MAP[locale]
    if (f == null) {
        f = SimpleDateFormat.getTimeInstance(SimpleDateFormat.DEFAULT, locale)
        FORMATTER_TIME_HUMAN_MAP.put(locale, f)
    }
    return f!!
}

@JvmOverloads
inline fun Date.printHumanReadableTime(locale: Locale = Locale.getDefault()): String {
    return getFormatterTimeHuman(locale).format(this)
}

@JvmOverloads
inline fun Date.printHumanReadableDate(locale: Locale = Locale.getDefault()): String {
    return getFormatterDateHuman(locale).format(this)
}

@JvmOverloads
inline fun Date.printHumanReadableDateTime(locale: Locale = Locale.getDefault()): String {
    return getFormatterDateTimeHuman(locale).format(this)
}

inline fun Date.printMachineReadableDate(): String = FORMATTER_DATE_MACHINE.format(this)

inline fun String.parseMachineReadableDate(): Date = FORMATTER_DATE_MACHINE.parse(this)!!

inline fun Date.printMachineReadableDateTime(): String = FORMATTER_ISO8601.format(this)

inline fun String.parseMachineReadableDateTime(): Date = FORMATTER_ISO8601.parse(this)!!
