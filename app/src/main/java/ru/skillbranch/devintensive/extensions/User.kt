package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

fun User.toUserView(): UserView {
    val nickname = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status = when {
        lastVisit == null -> "Не был"
        isOnline -> "online"
        else -> "был ${lastVisit?.humanizeDiff()}"
    }

    return UserView(
        id,
        fullName = "$firstName $lastName",
        avatar = avatar,
        nickName = nickname,
        initials = initials,
        status = status
    )
}
