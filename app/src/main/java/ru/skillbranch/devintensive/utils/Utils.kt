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

    fun transliteration(fullname: String, divider: String = " "): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        fun valid(str: String?) = str != null && str != "" && str != " "
        fun getFirst(str: String) = str[0].toUpperCase()

        var initials = ""
        initials += if (valid(firstName)) getFirst(firstName!!) else ""
        initials += if (valid(lastName)) getFirst(lastName!!) else ""

        return if (initials == "") null else initials
    }
}