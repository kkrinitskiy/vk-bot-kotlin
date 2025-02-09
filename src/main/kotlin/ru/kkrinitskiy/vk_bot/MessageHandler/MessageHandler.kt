package ru.kkrinitskiy.vk_bot.MessageHandler

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.objects.callback.MessageNew
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.kkrinitskiy.vk_bot.service.AdminService
import ru.kkrinitskiy.vk_bot.service.CockSizeService
import kotlin.random.Random

@Component
class MessageHandler(
    @Value("\${vk.adminId}") val adminId: Long,
    val vk: VkApiClient,
    val groupActor: GroupActor,
    val cockSizeService: CockSizeService,
    val adminService: AdminService
) {
    val r = Random.Default
    fun sendAnswerToUser(message: MessageNew, answer: String) {
        val randomId = r.nextInt(0, 1000)
        println("send answer $answer to ${message.`object`.message.peerId} randomId: $randomId")
        vk.messages()
            .sendUserIds(groupActor)
            .peerIds(message.`object`.message.peerId)
            .message(answer)
            .randomId(randomId)
            .execute()
    }

    fun cockSize(message: MessageNew) {
        sendAnswerToUser(message, cockSizeService.getSize(message))
    }

    fun cockRating(message: MessageNew) {
        sendAnswerToUser(message, cockSizeService.getChatRating(message))
    }

    fun weather(message: MessageNew) {
        sendAnswerToUser(message, "weather")

    }

    fun random(message: MessageNew) {
        sendAnswerToUser(message, "random")

    }

    fun remove(message: MessageNew) {
        val cmids = mutableListOf<Int>(message.`object`.message.conversationMessageId)
        if (message.`object`.message.fwdMessages != null) {
            val fwdMessages = message.`object`.message.fwdMessages
            for (fwdMessage in fwdMessages) {
                cmids.add(fwdMessage.conversationMessageId)
            }
        }
        vk.messages().deleteFull(groupActor).peerId(message.`object`.message.peerId).deleteForAll(true).cmids(cmids)
            .execute()
    }

    fun admin(message: MessageNew) {
        println("сообщение от админа?")
        if (message.`object`.message.fromId == adminId) {
            println("сообщение от админа!")
            val args = message.`object`.message.text.split(" ")
            println(args)

            if(args.size == 2){
                sendAnswerToUser(message, "admin")
                return
            }

            when (args[2]) {
                "stat" -> sendAnswerToUser(message, adminService.getStat())
            }
        }
    }
}