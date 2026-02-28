package com.barox.ticketflash.security.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.barox.ticketflash.annotation.RateLimit;
import com.barox.ticketflash.security.rate_limit.RedisRateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private final RedisRateLimiter rateLimiter;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception 
    {
        // Dùng header "X-Forwarded-For" nếu đứng sau nginx
        // Này là học nên lấy tạm
        // deloy thật chắc chắn dùng nginx
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }        
        // Nếu chỉ dùng ip thì nếu có 2 api, 1 cái giới hạn 10 req/phút, 1 cái giới hạn 20 req/phút 
        // thì khi lôi bucket lên thấy không khớp -> lỗi
        String redisKey = "rl:" + clientIp + ":" + request.getRequestURI();

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            log.info("Handler is not a HandlerMethod: {}", handler);
            return true;
        }
        try {
            RateLimit rateLimit = handlerMethod.getMethodAnnotation(RateLimit.class);
            log.info("RateLimit annotation: {}", rateLimit);
            if (rateLimit != null) {
                var probe = rateLimiter.tryAccess(redisKey, rateLimit.capacity(), rateLimit.refillTokens());
                if (!probe.isConsumed()) {
                    
                    long waitForRefillSecond = probe.getNanosToWaitForRefill() / 1_000_000_000; 

                    // Header juan
                    response.setHeader("Retry-After", String.valueOf(waitForRefillSecond));

                    response.setStatus(429);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Too many requests. Please try again in " + waitForRefillSecond + " seconds.\" }");
                    return false;
                }
            }
        } 
        catch (Exception e) {
            log.error("redis chết ngắc khi gặp key {}. ", redisKey, e.getMessage());
            // cho qua: fail-open
            return true;
        }
        return true;
    }
}
