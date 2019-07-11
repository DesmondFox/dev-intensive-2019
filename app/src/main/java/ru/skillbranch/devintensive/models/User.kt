package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    var isOnline: Boolean = false
) {
    constructor(firstName: String?, lastName: String?) :
            this(firstName = firstName, lastName = lastName, avatar = null, id = "0")

    data class Builder(
        var id: String = "0",
        var firstName: String? = null,
        var lastName: String? = null,
        var avatar: String? = null,
        var rating: Int = 0,
        var respect: Int = 0,
        var lastVisit: Date? = Date(),
        var isOnline: Boolean = false
    ) {
        fun id(id: String): Builder = apply { this.id = id  }
        fun firstName(firstName: String?): Builder = apply { this.firstName = firstName  }
        fun lastName(lastName: String?): Builder = apply { this.lastName = lastName  }
        fun avatar(avatar: String?): Builder = apply { this.avatar = avatar  }
        fun rating(rating: Int): Builder = apply { this.rating = rating  }
        fun respect(respect: Int): Builder = apply { this.respect = respect  }
        fun lastVisit(lastVisit: Date?): Builder = apply { this.lastVisit = lastVisit  }
        fun isOnline(isOnline: Boolean): Builder = apply { this.isOnline = isOnline  }
        fun build(): User = User(
            id = id,
            firstName = firstName,
            lastName = lastName,
            avatar = avatar,
            rating = rating,
            respect = respect,
            lastVisit = lastVisit,
            isOnline = isOnline
        )
    }

    companion object Factory {


        fun makeUser(
            fullname: String?
        ): User {
            val (fname, sname) = Utils.parseFullName(fullname)
            return User(fname, sname)
        }
    }
}