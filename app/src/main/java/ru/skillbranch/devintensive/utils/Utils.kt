package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullname: String?): Pair<String?, String?> = when (fullname) {
        null, "", " " -> Pair(null, null)
        else -> {
            val l = fullname.split(" ")
            if (l.size == 1)
                Pair(l[0], null)
            else
                Pair(l[0], l[1])
        }
    }
}