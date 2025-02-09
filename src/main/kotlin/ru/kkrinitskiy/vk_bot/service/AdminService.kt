package ru.kkrinitskiy.vk_bot.service

import org.springframework.stereotype.Service
import ru.kkrinitskiy.vk_bot.repository.UserRepository

@Service
class AdminService(var userRepository: UserRepository) {
    fun getStat(): String {
        val stringBuilder = StringBuilder()
        val users = userRepository.getAll()
        stringBuilder.append("всего пользователей: ${users.size}\n")
        if (users.isNotEmpty()) {
            for (user in users) {
                stringBuilder.append(user.toString()).append("\n")
            }
        }
        return stringBuilder.toString()
    }
}