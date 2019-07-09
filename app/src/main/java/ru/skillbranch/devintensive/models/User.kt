package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id : String,
    var firstName : String?,
    var lastName : String?,
    var avatar : String?,
    var rating : Int = 0,
    var respect : Int = 0,
    var lastVisit : Date? = Date(),
    var isOnline : Boolean = false
) {
    constructor(firstName : String?, lastName: String?) :
            this(firstName = firstName, lastName = lastName, avatar = null, id = "0")

    companion object Factory {
        fun makeUser(fullname: String?): User {
            val (fname, sname) = Utils.parseFullName(fullname)
            return User(fname, sname)
        }
    }
}