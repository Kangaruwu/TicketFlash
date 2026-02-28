package com.barox.ticketflash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;

@Configuration
public class RabbitMQConfig {
    
    // Luồng:
    // Khi user đặt vé, hệ thống trừ vé, rồi bắn 1 message vào booking queue
    // Queue này không ai nghe, nó chỉ tồn tại để chứa message trong 15 phút
    // Sau 15 phút, message sẽ được chuyển sang DLX queue
    // Tại DLX queue thì mới có người nghe
    // để moi message ra, lấy booking ID tra DB rồi hoàn lại vé nếu booking vẫn còn pending

    public static final String BOOKING_EXCHANGE = "booking.direct.exchange";
    public static final String BOOKING_QUEUE = "booking.queue";
    public static final String BOOKING_ROUTING_KEY = "booking.routing.key";

    public static final String DLX_EXCHANGE = "booking.dlx.exchange";
    public static final String DLX_QUEUE = "booking.dlx.queue";
    public static final String DLX_ROUTING_KEY = "booking.dlx.routing.key";

    @Bean 
    public DirectExchange bookingExchange() {
        return new DirectExchange(BOOKING_EXCHANGE);
    }

    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    @Bean
    // Không ai nghe hay lấy message từ queue này
    public Queue bookingQueue() {
        Map<String, Object> args = new HashMap<>();
        // Khi message die thì văng vào DLX
        args.put("x-dead-letter-exchange", DLX_EXCHANGE);
        // Với key này
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        // Message sống trong queue 2 phút (120000 ms) rồi die
        args.put("x-message-ttl", 120000);
        return QueueBuilder
            .durable(BOOKING_QUEUE)
            .withArguments(args)
            .build();
    }

    @Bean
    public Queue dlxQueue() {
        return QueueBuilder
            .durable(DLX_QUEUE)
            .build();  
    }

    @Bean
    public Binding bookingBinding() {
        return BindingBuilder
            .bind(bookingQueue())
            .to(bookingExchange())
            .with(BOOKING_ROUTING_KEY);
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder
            .bind(dlxQueue())
            .to(dlxExchange())
            .with(DLX_ROUTING_KEY);
    }

    // Không để RabbitMQ tự convert message, security đấm vào mặt
    // Với lại chỉ truyền string hoặc DTO không truyền UUID
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

}
