package net.xpece.android.text

import java.text.NumberFormat
import java.util.*


object Formatter {
    val CS_CZ = Locale("cs", "CZ")
    val CZK = Currency.getInstance("CZK")!!

    @JvmOverloads
    fun getCurrencyFormatter(currency: Currency, minimumFractionDigits: Int = 0, locale: Locale = Locale.getDefault()): NumberFormat {
        val format = NumberFormat.getCurrencyInstance(locale)
        format.currency = currency
        format.minimumFractionDigits = minimumFractionDigits
        return format
    }
}
