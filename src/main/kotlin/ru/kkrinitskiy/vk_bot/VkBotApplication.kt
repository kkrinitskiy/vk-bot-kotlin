package ru.kkrinitskiy.vk_bot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VkBotApplication

fun main(args: Array<String>) {
	runApplication<VkBotApplication>(*args)
}
