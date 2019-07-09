package ru.skillbranch.devintensive.extentions

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    return SimpleDateFormat(pattern, Locale.forLanguageTag("ru")).format(this)
}

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