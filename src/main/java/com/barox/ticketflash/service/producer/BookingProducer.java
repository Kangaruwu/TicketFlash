package com.barox.ticketflash.service.producer;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class BookingProducer {
    
    private final RabbitTemplate rabbitTemplate;    

    public void sendBookingToQueue(UUID bookingId) {
        rabbitTemplate.convertAndSend(
            "booking.direct.exchange", 
            "booking.routing.key", 
            bookingId.toString()
        );
        log.info("Sending booking ID {} to booking queue", bookingId);
    }

}
