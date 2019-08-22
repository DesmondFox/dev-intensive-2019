package ru.skillbranch.devintensive.models

data class Profile(
    val firstName: String,
    val lastname: String,
    val about: String,
    val repository: String,
    val rating: Int = 0,
    val respect: Int = 0
) {
    val rank: String = "Junior Android Developer"
    val nickName = "John Doe" // TODO implement

    fun toMap(): Map<String, Any> = mapOf(
        "nickName" to nickName,
        "rank" to rank,
        "firstName" to firstName,
        "lastname" to lastname,
        "about" to about,
        "repository" to repository,
        "rating" to rating,
        "respect" to respect
    )
}