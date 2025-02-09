package ru.kkrinitskiy.vk_bot.service

import com.vk.api.sdk.objects.callback.MessageNew
import org.springframework.stereotype.Service
import ru.kkrinitskiy.vk_bot.repository.UserRepository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class CockSizeService(val userRepository: UserRepository) {
    fun getSize(message: MessageNew): String {
        val fromId = message.`object`.message.fromId
        val user = userRepository.getById(fromId)!!
        val dateTimeNow = LocalDateTime.now()
        if (user.lastMeasurement == null || ChronoUnit.MINUTES.between(user.lastMeasurement, dateTimeNow) >= 2) {
            user.lastMeasurement = dateTimeNow
            user.penisLength = Random().nextInt(-3, 43)
            user.penisMessage = getPenisMessage(user.penisLength!!)
        }
        println("следующее измерение будет доступно: ${user.lastMeasurement?.plusMinutes(2)}")
        return user.penisMessage
    }

    private fun getPenisMessage(length: Int): String {
        val smile =
            if (length < 0) "\uD83D\uDD73"
            else if (length in 0..4) "\uD83D\uDE22"
            else if (length in 5..9) "\uD83E\uDD7A"
            else if (length in 10..14) "\uD83D\uDE10"
            else if (length in 15..19) "\uD83D\uDE0F"
            else if (length in 20..24) "\uD83E\uDD29"
            else if (length in 25..29) "\uD83D\uDE44"
            else if (length in 30..34) "\uD83E\uDEE0"
            else if (length in 35..39) "\uD83D\uDCA3"
            else "\uD83E\uDD47"
        return "Твой размер: ${length}см $smile"
    }

    fun getChatRating(message: MessageNew): String {
        val rating = userRepository.getAllUsersByPeerId(message.`object`.message.peerId).sortedWith(compareBy(nullsLast()) { it.penisLength })
        val stringBuilder = StringBuilder()
        stringBuilder.append("Cocksize rating:\n")
        println(rating.size)
        for (i in rating.indices) {
            stringBuilder.append("${i + 1}) ${if (rating[i].lastMeasurement != null) "${rating[i].name} - ${rating[i].penisMessage.lowercase()}" else "${rating[i].name} - пока не измерен"}\n")
        }
        return stringBuilder.toString()
    }
}
