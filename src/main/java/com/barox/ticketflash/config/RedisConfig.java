package com.barox.ticketflash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;

@Configuration
public class RedisConfig {
    
    /**
     * @param connectionFactory Thằng này do Spring Boot tự động tạo ra từ application.yml
     * @return Kết nối byte[] thuần túy để inject vào RedisRateLimiter
     */
    @Bean
    public StatefulRedisConnection<byte[], byte[]> bucket4jConnection(RedisConnectionFactory connectionFactory) {
        if (connectionFactory instanceof LettuceConnectionFactory lettuceConnectionFactory) {
            RedisClient nativeClient = (RedisClient) lettuceConnectionFactory.getNativeClient();
            return nativeClient.connect(ByteArrayCodec.INSTANCE);
        }
        throw new IllegalStateException("Unsupported RedisConnectionFactory: " + connectionFactory.getClass());
    }


}
