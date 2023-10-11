package io.kikiriki.sgmovie.utils.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String?.toDate(pattern: String = "yyyy-MM-dd") : Date? {
    if (this == null) return null
    val sdf = SimpleDateFormat(pattern, Locale.ROOT)
    return sdf.parse(this)
}

fun Date?.format(pattern: String = "yyyy-MM-dd") : String? {
    if (this == null) return null
    val sdf = SimpleDateFormat(pattern, Locale.ROOT)
    return sdf.format(this)
}

fun Date?.orNow() : Date {
    return this ?: return Date()
}