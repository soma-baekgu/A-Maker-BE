package com.backgu.amaker.realtime.config

import com.backgu.amaker.infra.redis.session.SessionRedisData
import com.backgu.amaker.realtime.server.config.ServerConfig
import com.backgu.amaker.realtime.session.service.SessionDeleteSubscriber
import io.lettuce.core.SocketOptions
import io.lettuce.core.cluster.ClusterClientOptions
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@Profile("prod")
@EnableRedisRepositories
class ProdRedisConfig(
    private val clusterConfigProperties: ClusterConfigProperties,
) {
    @Value("\${spring.data.redis.password}")
    lateinit var password: String

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val clusterConfiguration = RedisClusterConfiguration(clusterConfigProperties.nodes)
        clusterConfiguration.maxRedirects = clusterConfigProperties.maxRedirects
        clusterConfiguration.password = RedisPassword.of(password)

        val socketOptions =
            SocketOptions
                .builder()
                .connectTimeout(Duration.ofMillis(100L))
                .keepAlive(true)
                .build()

        val clusterTopologyRefreshOptions =
            ClusterTopologyRefreshOptions
                .builder()
                .dynamicRefreshSources(true)
                .enableAllAdaptiveRefreshTriggers()
                .enablePeriodicRefresh(Duration.ofMinutes(30L))
                .build()

        val clientOptions =
            ClusterClientOptions
                .builder()
                .topologyRefreshOptions(clusterTopologyRefreshOptions)
                .socketOptions(socketOptions)
                .build()

        val clientConfiguration =
            LettuceClientConfiguration
                .builder()
                .clientOptions(clientOptions)
                .commandTimeout(Duration.ofMillis(3000L))
                .build()

        return LettuceConnectionFactory(clusterConfiguration, clientConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, SessionRedisData> {
        val template = RedisTemplate<String, SessionRedisData>()
        template.connectionFactory = redisConnectionFactory()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        return template
    }

    @Bean
    fun messageListenerAdapter(sessionDeleteSubscriber: SessionDeleteSubscriber): MessageListenerAdapter =
        MessageListenerAdapter(sessionDeleteSubscriber, "dropOutSessions")

    @Bean
    fun redisContainer(
        messageListenerAdapter: MessageListenerAdapter,
        topic: ChannelTopic,
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(redisConnectionFactory())
        container.addMessageListener(messageListenerAdapter, topic)
        return container
    }

    @Bean
    fun topic(serverConfig: ServerConfig): ChannelTopic = ChannelTopic(serverConfig.id)
}
