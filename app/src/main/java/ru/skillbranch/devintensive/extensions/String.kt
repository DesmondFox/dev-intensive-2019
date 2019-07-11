package ru.skillbranch.devintensive.extensions

fun String.truncate(visibleCount: Int = 16): String {
    var truncated = this.replace(Regex("""\A\W+"""), "")
    truncated = truncated.take(visibleCount)

    var trCount = 0
    var refCount = 0
    for ((i, c) in this.withIndex()) {
        if (c != ' ')
            refCount++

        if (i < truncated.length && c != ' ')
            trCount++
    }
    truncated = truncated.replace(Regex("""\s+$"""), "")

    // Если исходного ПОЛЕЗНОГО текста было больше чем обрезанного, тогда добавляем "..."
    truncated += if (trCount < refCount) "..." else ""
    return truncated
}

fun String.stripHtml(): String {
    return this
        .replace(Regex("""<[^>]*>"""), "")  // HTML tags
        .replace(Regex("""\&[A-z0-9#]+\;"""), "") // escape последовательности
        .replace(Regex("""\ {2,}"""), " ")  // 2 и более пробелы
}