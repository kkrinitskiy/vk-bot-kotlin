package ru.kkrinitskiy.vk_bot.repository

import org.springframework.stereotype.Repository
import ru.kkrinitskiy.vk_bot.model.User

@Repository
class UserRepository {
    private val users = mutableListOf<User>()
    fun getAll(): List<User> {
        return users
    }

    fun addUser(user: User) {
        if (exists(user.id)) {
            getById(user.id)!!.peerIds.add(user.peerIds.first())
            return
        }
        users.add(user)
    }
    fun getById(id: Long): User? {
        return users.find { it.id == id }
    }
    fun exists(id: Long): Boolean {
        return users.any { it.id == id }
    }

    fun getAllUsersByPeerId(peerId: Long): List<User> {
        return users.filter { it.peerIds.contains(peerId) }
    }
}