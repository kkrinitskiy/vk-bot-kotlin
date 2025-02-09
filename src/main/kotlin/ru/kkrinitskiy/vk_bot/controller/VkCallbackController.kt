package ru.kkrinitskiy.vk_bot.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.kkrinitskiy.vk_bot.service.CallbackService

@Slf4j
@RestController
class VkCallbackController(
    private val callbackService: CallbackService,
    private val objectMapper: ObjectMapper
) {
    private val log = KotlinLogging.logger {}

    @PostMapping
    fun handleVkCallback(@RequestBody json: String): String {
        log.info { "Handling event: $json" }

        try {
            val node = objectMapper.readTree(json)
            if (node.has("type") && node.get("type").textValue() == "confirmation") {
                return callbackService.confirmation()
            }
            return callbackService.parse(json)
        } catch (e: Exception) {
            log.error(e) { "Exception while handling event: $json" }
        }
        return ""
    }
}