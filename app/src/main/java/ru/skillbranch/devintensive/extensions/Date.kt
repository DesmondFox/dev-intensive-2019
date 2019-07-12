package ru.skillbranch.devintensive.extensions

import java.lang.Math.abs
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    return SimpleDateFormat(pattern, Locale.forLanguageTag("ru")).format(this)
}

//const val sec = 1000
//const val min = 60*sec
//const val hour = 60*min
//const val day = 24*hour

enum class TimeUnits(val v: Long) {
    SECOND(1000L),
    MINUTE(SECOND.v * 60L),
    HOUR(MINUTE.v * 60L),
    DAY(HOUR.v * 24L);

    fun plural(v: Long): String {
        val form1 = listOf("секунд", "минут", "часов", "дней")
        val form2 = listOf("секунду", "минуту", "час", "день")
        val form3 = listOf("секунды", "минуты", "часа", "дня")

        val lastNumber = v % 10
        val penultimateNumber = (v % 100 - lastNumber) / 10
        return "$v ${
        when {
            penultimateNumber == 1L -> form1[this.ordinal]
            lastNumber == 0L || lastNumber in 5..9 -> form1[this.ordinal]
            lastNumber in 2..4 -> form3[this.ordinal]
            else -> form2[this.ordinal]
        }}"
    }
}


fun Date.add(
    value: Int,
    timeUnit: TimeUnits
): Date {
    this.time = this.time + timeUnit.v * value
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {

    val msdiff = this.time - date.time

    // Если msdiff < 0 - был
    // Если msdiff > 0 - будет

    val absdiff = abs(msdiff)
    return if (msdiff > 0) {
        when (absdiff) {
            in 0 * TimeUnits.SECOND.v..1 * TimeUnits.SECOND.v -> "только что"
            in 1 * TimeUnits.SECOND.v..45 * TimeUnits.SECOND.v -> "через несколько секунд"
            in 45 * TimeUnits.SECOND.v..75 * TimeUnits.SECOND.v -> "через минуту"
            in 75 * TimeUnits.SECOND.v..45 * TimeUnits.MINUTE.v -> "через ${TimeUnits.MINUTE.plural(absdiff / TimeUnits.MINUTE.v)}"
            in 45 * TimeUnits.MINUTE.v..75 * TimeUnits.MINUTE.v -> "через час"
            in 75 * TimeUnits.MINUTE.v..22 * TimeUnits.HOUR.v -> "через ${TimeUnits.HOUR.plural(absdiff / TimeUnits.HOUR.v)}"
            in 22 * TimeUnits.HOUR.v..26 * TimeUnits.HOUR.v -> "через день"
            in 26L * TimeUnits.HOUR.v..360L * TimeUnits.DAY.v -> "через ${TimeUnits.DAY.plural(absdiff / TimeUnits.DAY.v)}"
            else -> "более чем через год"
        }
    } else {
        when (absdiff) {
            in 0 * TimeUnits.SECOND.v..1 * TimeUnits.SECOND.v -> "только что"
            in 1 * TimeUnits.SECOND.v..45 * TimeUnits.SECOND.v -> "несколько секунд назад"
            in 45 * TimeUnits.SECOND.v..75 * TimeUnits.SECOND.v -> "минуту назад"
            in 75 * TimeUnits.SECOND.v..45 * TimeUnits.MINUTE.v -> "${TimeUnits.MINUTE.plural(absdiff / TimeUnits.MINUTE.v)} назад"
            in 45 * TimeUnits.MINUTE.v..75 * TimeUnits.MINUTE.v -> "час назад"
            in 75 * TimeUnits.MINUTE.v..22 * TimeUnits.HOUR.v -> "${TimeUnits.HOUR.plural(absdiff / TimeUnits.HOUR.v)} назад"
            in 22 * TimeUnits.HOUR.v..26 * TimeUnits.HOUR.v -> "день назад"
            in 26L * TimeUnits.HOUR.v..360L * TimeUnits.DAY.v -> "${TimeUnits.DAY.plural(absdiff / TimeUnits.DAY.v)} назад"
            else -> "более года назад"
        }
    }
}