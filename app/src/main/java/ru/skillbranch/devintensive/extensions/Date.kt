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
    MINUTE(SECOND.v * 60),
    HOUR(MINUTE.v * 60),
    DAY(HOUR.v * 24),
    YEAR(DAY.v * 365);

    fun plural(v: Int): String {
        val form1 = listOf("секунд", "минут", "часов", "дней", "лет")
        val form2 = listOf("секунду", "минуту", "час", "день", "год")
        val form3 = listOf("секунды", "минуты", "часа", "дня", "года")

        val lastNumber = v % 10
        val penultimateNumber = (v % 100 - lastNumber) / 10
        return "$v ${
            when {
                penultimateNumber == 1 -> form1[this.ordinal]
                lastNumber == 0 || lastNumber in 5..9 -> form1[this.ordinal]
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