package ru.kkrinitskiy.vk_bot.service

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.events.callback.CallbackApi
import com.vk.api.sdk.objects.callback.MessageNew
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CallbackService(
    @Value("\${vk.confirmationCode}") val confirmationCode: String,
    @Value("\${vk.secretKey}") val secretKey: String,
    private val vk: VkApiClient,
    private val groupActor: GroupActor,
    private val messageHandlerService: MessageHandlerService
) : CallbackApi(confirmationCode, secretKey) {

    override fun messageNew(groupId: Int?, message: MessageNew) {
        messageHandlerService.handle(message)
    }
}