package com.barox.ticketflash.service.impl;

import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.barox.ticketflash.dto.response.PaymentResponse;
import com.barox.ticketflash.entity.Booking;
import com.barox.ticketflash.enums.BookingStatus;
import com.barox.ticketflash.event.PaymentSuccessEvent;
import com.barox.ticketflash.exception.DataNotFoundException;
import com.barox.ticketflash.repository.BookingRepository;
import com.barox.ticketflash.security.CustomUserDetails;
import com.barox.ticketflash.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final BookingRepository bookingRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    @Transactional
    public PaymentResponse processPayment(UUID bookingId, CustomUserDetails userDetails) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new DataNotFoundException("Booking not found with ID: " + bookingId));

        if (!booking.getEmail().equals(userDetails.getEmail())) {
            throw new DataNotFoundException("Booking not found for the current user");
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("Only pending bookings can be processed for payment");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalStateException("Payment unsuccessful: Booking has been expired");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        publisher.publishEvent(new PaymentSuccessEvent(
            booking.getEmail(), 
            booking.getEvent().getName(), 
            booking.getTotalTicket()
        ));

        return new PaymentResponse(
            "Thanh toán thành công cho booking ID: " + bookingId + " với email: " + booking.getEmail()
        );
    }
}
