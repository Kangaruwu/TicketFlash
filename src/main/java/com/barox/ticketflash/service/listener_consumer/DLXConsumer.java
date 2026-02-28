package com.barox.ticketflash.service.listener_consumer;

import java.util.UUID;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.barox.ticketflash.config.RabbitMQConfig;
import com.barox.ticketflash.entity.Booking;
import com.barox.ticketflash.enums.BookingStatus;
import com.barox.ticketflash.exception.DataNotFoundException;
import com.barox.ticketflash.repository.BookingRepository;
import com.barox.ticketflash.service.BookingService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class DLXConsumer {

    private final BookingRepository bookingRepository;
    private final BookingService bookingService;

    @RabbitListener(queues = RabbitMQConfig.DLX_QUEUE)
    @Transactional
    public void handleExpiredBooking(String message) {
        try {
            UUID bookingId = UUID.fromString(message);
            log.info("Received dlx:" + bookingId);

            Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new DataNotFoundException("Booking not found for ID " + bookingId)
            );

            if (BookingStatus.PENDING.equals(booking.getStatus())) {
                booking.setStatus(BookingStatus.CANCELLED);
                bookingRepository.save(booking);

                // hoàn vé
                bookingService.restoreTickets(booking);
            }
        } catch (Exception e) {
            // Ack the message to prevent infinite requeue loop
            log.error("Failed to process expired booking message '{}': {}", message, e.getMessage(), e);
        }
    }

}
