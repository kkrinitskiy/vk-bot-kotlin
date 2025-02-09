package ru.kkrinitskiy.vk_bot.configuration

import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import com.vk.api.sdk.objects.groups.GroupFull
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MainConfig(
    @Value("\${vk.groupId}") private val groupId: Long,
    @Value("\${vk.token}") private val accessToken: String) {

    @Bean
    fun groupActor(): GroupActor {
        return GroupActor(groupId, accessToken)
    }

    @Bean
    fun vkApiClient(): VkApiClient {
        return VkApiClient(HttpTransportClient())
    }

    @Bean
    fun group(vk: VkApiClient, groupActor: GroupActor): GroupFull{
        return vk.groups().getByIdObject(groupActor).groupId(groupId.toString()).execute().groups[0]
    }
}