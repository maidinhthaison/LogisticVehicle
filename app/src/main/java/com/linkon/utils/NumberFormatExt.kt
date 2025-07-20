package com.linkon.utils

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale


fun formatPriceToCurrency(price: String?): String {
    val numberFormat : NumberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
    numberFormat.currency= Currency.getInstance("USD")
    numberFormat.minimumFractionDigits = 3
    return numberFormat.format(price?.toFloat())
}
fun formatPriceTo3Digits(price: String?): String{
    return "%.3f".format(price?.toFloat())
}
