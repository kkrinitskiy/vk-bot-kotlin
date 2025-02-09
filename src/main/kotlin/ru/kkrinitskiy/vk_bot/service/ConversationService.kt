package ru.kkrinitskiy.vk_bot.service

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.objects.callback.MessageNew
import org.springframework.stereotype.Service
import ru.kkrinitskiy.vk_bot.model.Conversation
import ru.kkrinitskiy.vk_bot.repository.ConversationRepository

@Service
class ConversationService(
    private val vkApiService: VkApiService,
    private val vk: VkApiClient,
    private val conversationRepository: ConversationRepository
) {
    fun addNewConversation(message: MessageNew) {
        val peerId = message.`object`.message.peerId

        if (!conversationRepository.exists(peerId)) {
            val newConversation = Conversation(peerId, vkApiService.getConversationName(peerId))
            println("New conversation $newConversation")
            conversationRepository.addConversation(newConversation)
        }
    }
}