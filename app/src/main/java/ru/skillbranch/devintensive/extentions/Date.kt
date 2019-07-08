package ru.skillbranch.devintensive.extentions

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    return SimpleDateFormat(pattern, Locale.forLanguageTag("ru")).format(this)
}