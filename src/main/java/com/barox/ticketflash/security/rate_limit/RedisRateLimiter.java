package com.barox.ticketflash.security.rate_limit;

import java.time.Duration;
import org.springframework.stereotype.Service;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.distributed.BucketProxy;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ClientSideConfig;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedisRateLimiter {
    
    private final LettuceBasedProxyManager<byte[]> bucketProxyManager;

    // inject conn từ ngoài vào không nhận redisUri để kết nối trong class này
    public RedisRateLimiter(StatefulRedisConnection<byte[], byte[]> connection) { 
        // tạo cấu hình
        ClientSideConfig clientSideConfig = ClientSideConfig.getDefault()
            .withExpirationAfterWriteStrategy(
                ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofSeconds(10))
            );
        // bơm vào manager
        this.bucketProxyManager = LettuceBasedProxyManager.builderFor(connection)
            .withClientSideConfig(clientSideConfig)
            .build();
    }
    
    private BucketConfiguration getConfiguration(int capacity, int refillTokens) {
        Bandwidth limit = Bandwidth.builder()
                .capacity(capacity) 
                .refillIntervally(refillTokens, Duration.ofMinutes(1)) // Hồi mỗi 1 phút
                .build();

        return BucketConfiguration.builder()
                .addLimit(limit)
                .build();
    }

    // Lấy bucket (IP, UserID, API Key)
    private BucketProxy resolveBucket(String key, int capacity, int refillTokens) {
        return bucketProxyManager.builder().build(key.getBytes(), () -> this.getConfiguration(capacity, refillTokens));    
    }

    // Kiểm tra quyền truy cập
    // ConsumptionProbe (Cây dò tiêu thụ). Nó chứa mọi thông tin cần từ Redis trả về trong một lần gọi mạng duy nhất
    // để lấy thời gian chờ
    public ConsumptionProbe tryAccess(String identifier, int capacity, int refillTokens) {
        BucketProxy bucket = resolveBucket(identifier, capacity, refillTokens);
        return bucket.tryConsumeAndReturnRemaining(1);
    }
}
