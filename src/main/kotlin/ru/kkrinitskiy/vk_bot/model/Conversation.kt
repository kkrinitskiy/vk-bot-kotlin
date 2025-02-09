package ru.kkrinitskiy.vk_bot.model

class Conversation (val id: Long, var name: String) {
    val userIds = mutableListOf<String>()
    override fun toString(): String {
        return "Conversation(id=$id, name='$name', members=[${userIds.size}])"
    }
}