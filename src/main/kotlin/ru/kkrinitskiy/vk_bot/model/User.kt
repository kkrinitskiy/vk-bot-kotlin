package ru.kkrinitskiy.vk_bot.model

import java.time.LocalDateTime

class User(val id: Long, var name: String) {
    val peerIds = mutableListOf<Long>()
    var penisLength: Int? = null
    var penisMessage: String = ""
    var lastMeasurement: LocalDateTime? = null
    override fun toString(): String {
        return "User(id=$id, name='$name', peerIds='$peerIds')"
    }
}