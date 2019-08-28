package ru.skillbranch.devintensive.utils

import android.annotation.SuppressLint
import android.content.Context
import java.util.*
import kotlin.math.roundToInt

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

    @SuppressLint("DefaultLocale")
    fun transliteration(fullname: String, divider: String = " "): String {
        val transMap = mapOf(
            ' ' to divider,
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya"
        )
//        when (fullname) {
//            null, "", " " -> return
//        }
        var trasliterated = ""
        fullname.asSequence()
            .map {

                if (it.isLetter() && it.toLowerCase() in transMap.keys) {
                    if (it.isUpperCase())
                        trasliterated += transMap[it.toLowerCase()]?.capitalize()
                    else if (it.isLowerCase())
                        trasliterated += transMap[it.toLowerCase()]
                } else if (it == ' ') {
                    trasliterated += divider
                } else {
                    trasliterated += it.toString()
                }
            }
            .joinToString()

        return trasliterated
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        fun valid(str: String?) = str != null && str != "" && str != " "
        fun getFirst(str: String) = str[0].toUpperCase()

        var initials = ""
        initials += if (valid(firstName)) getFirst(firstName!!) else ""
        initials += if (valid(lastName)) getFirst(lastName!!) else ""

        return if (initials == "") null else initials
    }

    fun convertDpToPx(context: Context, dp: Float) =
        (dp * context.resources.displayMetrics.density).roundToInt()

    fun convertPxToDp(context: Context, px: Float) =
        (px / context.resources.displayMetrics.density).roundToInt()

    fun convertSpTpPx(context: Context, sp: Float) =
        sp * context.resources.displayMetrics.scaledDensity

    fun isRepositoryUrlValid(url: String?): Boolean {
        url ?: return false
        val invalidNames = setOf(
            "enterprise",
            "features",
            "topics",
            "collections",
            "trending",
            "events",
            "marketplace",
            "pricing",
            "nonprofit",
            "customer-stories",
            "security",
            "login",
            "join"
        )

        val ignoreSequence = invalidNames.joinToString(separator = "|")
        val pattern1 = """^https:\/\/github\.com\/(?!$ignoreSequence)[A-z0-9]+$""".toRegex()
        val pattern2 = """^https:\/\/www\.github\.com\/(?!$ignoreSequence)[A-z0-9]+$""".toRegex()
        val pattern3 = """^www\.github\.com\/(?!$ignoreSequence)[A-z0-9]+$""".toRegex()
        val pattern4 = """^github\.com\/(?!$ignoreSequence)[A-z0-9]+$""".toRegex()

        return url.contains(pattern1) ||
                url.contains(pattern2) ||
                url.contains(pattern3) ||
                url.contains(pattern4)
    }
}