package ru.kkrinitskiy.vk_bot.service

import com.vk.api.sdk.objects.callback.MessageNew
import com.vk.api.sdk.objects.groups.GroupFull
import org.springframework.stereotype.Service
import ru.kkrinitskiy.vk_bot.MessageHandler.MessageHandler

@Service
class MessageHandlerService(
    private val group: GroupFull,
    val messageHandler: MessageHandler,
    val conversationService: ConversationService,
    val userService: UserService,
    val vkApiService: VkApiService,
) {
    val groupAppeal = "[club%d|@%s]".format(group.id, group.screenName)
    fun handle(message: MessageNew) {
        userService.addAllUsersFromConversation(message)
        if (isMessageDirectToBot(message)) {
            val input =
                message.`object`.message.text.substring(groupAppeal.length, message.`object`.message.text.length)
                    .trimIndent()
            println("input: $input")
            when {
                input.matches(Regex("/cocksize")) -> messageHandler.cockSize(message)
                input.matches(Regex("/rating")) -> messageHandler.cockRating(message)
                input.matches(Regex("/remove")) -> messageHandler.remove(message)
                input.matches(Regex("/admin .*")) -> messageHandler.admin(message)
                else -> println("не верный ввод: $input, ${message.`object`.message.text}")
            }
        }
    }

    private fun isMessageDirectToBot(message: MessageNew): Boolean {
        return message.`object`.message.text.contains(groupAppeal)
    }
}