package ru.kkrinitskiy.vk_bot

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.exceptions.ApiExtendedException
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import ru.kkrinitskiy.vk_bot.model.User
import ru.kkrinitskiy.vk_bot.repository.UserRepository

@Component
class InitBot(val vk: VkApiClient, val groupActor: GroupActor, val userRepository: UserRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        getConversationsAndMembers()
    }

    private fun getConversationsAndMembers() {
        println("Инициализация...")
        for (peerId in 2000000001L..2000000200L) {
            try {
                val profiles = vk.messages()
                    .getConversationMembers(groupActor, peerId)
                    .extended(true)
                    .execute()
                    .profiles

                for (profile in profiles) {
                    val newUser = User(profile.id, profile.firstName)
                    newUser.peerIds.add(peerId)
                    userRepository.addUser(newUser)
                }
            } catch (e: ApiExtendedException) {
                when (e.code) {
                    917 -> {
                        println("Нет доступа к беседе $peerId: ${e.message}")
                    }
                    927 -> {
                        return
                    }
                    else -> {
                        println("Ошибка при обработке беседы $peerId: ${e.message}")
                    }
                }
            } catch (e: Exception) {
                println("Неизвестная ошибка при обработке беседы $peerId: ${e.message}")
            }
        }
        println("Инициализация завершена.")
    }
}