package com.linkon.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun formatDateTimeServer(dateInput: String, patternInput: String, patternOutput: String): String {
    val formatter: DateFormat = SimpleDateFormat(patternInput, Locale.getDefault())
    val date: Date = formatter.parse(dateInput) as Date
    return SimpleDateFormat(patternOutput, Locale.getDefault()).format(date)
}

fun formatDate(date: Date, outputFormat: String): String {
    val localDate = LocalDateTime.ofInstant(date.toInstant(), TimeZone.getDefault().toZoneId())
    val outFormatter = DateTimeFormatter.ofPattern(outputFormat)
    return localDate.format(outFormatter)
}
