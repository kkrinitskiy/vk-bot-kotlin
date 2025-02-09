package ru.kkrinitskiy.vk_bot.repository

import org.springframework.stereotype.Repository
import ru.kkrinitskiy.vk_bot.model.Conversation

@Repository
class ConversationRepository {
    private val conversations = mutableListOf<Conversation>()
    fun addConversation(conversation: Conversation) {
        conversations.add(conversation)
    }
    fun findById(id: Long): Conversation? {
        return conversations.find { it.id == id }
    }
    fun exists(id: Long): Boolean {
        return conversations.any { it.id == id }
    }
}