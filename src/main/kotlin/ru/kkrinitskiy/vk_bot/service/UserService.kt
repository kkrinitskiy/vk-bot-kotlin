package ru.kkrinitskiy.vk_bot.service

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.objects.callback.MessageNew
import org.springframework.stereotype.Service
import ru.kkrinitskiy.vk_bot.model.User
import ru.kkrinitskiy.vk_bot.repository.UserRepository

@Service
class UserService(
    val vk: VkApiClient,
    val groupActor: GroupActor,
    val vkApiService: VkApiService,
    val userRepository: UserRepository
) {
    fun addNewUser(message: MessageNew) {
        val fromId = message.`object`.message.fromId
        if (!userRepository.exists(fromId)) {
            val newUser = User(fromId, vkApiService.getUserName(fromId))
            newUser.peerIds.add(message.`object`.message.peerId)
            println("newUser has been added: $newUser")
            userRepository.addUser(newUser)
        }
    }

    //    1) иметь всех участников беседы
//    2) сократить количество запросов к апи -> проверять совпадает ли количество участников беседы из сообщения с тем которое у нас хранится
    fun addAllUsersFromConversation(message: MessageNew) {
        val peerId = message.`object`.message.peerId
        val profiles = vk.messages().getConversationMembers(groupActor, peerId).execute().profiles
        println("получили всех участников чата из апи")
        for (user in profiles) {
            if(userRepository.exists(user.id) && !userRepository.getById(user.id)!!.peerIds.contains(peerId)) {
                    userRepository.getById(user.id)!!.peerIds.add(user.id)
            }
            if (!userRepository.exists(user.id)) {
                val newUser = User(user.id, user.firstName)
                newUser.peerIds.add(message.`object`.message.peerId)
                userRepository.addUser(newUser)
                println("Сохранен: $newUser")
            }
        }
    }
}