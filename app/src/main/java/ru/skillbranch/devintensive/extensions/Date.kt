package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    return SimpleDateFormat(pattern, Locale.forLanguageTag("ru")).format(this)
}

//const val sec = 1000
//const val min = 60*sec
//const val hour = 60*min
//const val day = 24*hour

enum class TimeUnits(val v: Int) {
    SECOND(1000),
    MINUTE(SECOND.v*60),
    HOUR(MINUTE.v*60),
    DAY(HOUR.v*24)
}


fun Date.add(
    value: Int,
    timeUnit: TimeUnits
): Date {
    this.time = this.time + timeUnit.v*value
    return this
}