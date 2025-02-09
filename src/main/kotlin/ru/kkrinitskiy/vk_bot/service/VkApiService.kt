package ru.kkrinitskiy.vk_bot.service

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.objects.users.Fields
import org.springframework.stereotype.Service

@Service
class VkApiService(private val vk: VkApiClient, private val groupActor: GroupActor) {
    fun getConversationName(peerId: Long): String {
        return vk.messages().getConversations(groupActor).execute().items.first { conversation ->
            conversation.conversation.peer.id == peerId
        }.conversation.chatSettings.title
    }

    fun getUserName(fromId: Long): String {
        return vk.users().get(groupActor).userIds(fromId.toString()).fields(Fields.NICKNAME).execute().first().firstName
    }
}