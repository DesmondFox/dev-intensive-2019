package ru.skillbranch.devintensive.models

import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var lastId = 0
        fun makeMessage(
            from: User?,
            chat: Chat,
            date: Date = Date(),
            type: String = "text",
            payload: Any?
        ): BaseMessage {
            return when (type) {
                "image" -> ImageMessage("${lastId++}", from, chat, image = payload as String, date = date)
                else -> TextMessage("${lastId++}", from, chat, text = payload as String, date = date)
            }
        }
    }
}