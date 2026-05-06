package com.barox.ticketflash.service.impl;

import com.barox.ticketflash.dto.request.CheckInRequest;
import com.barox.ticketflash.dto.response.CheckInResponse;
import com.barox.ticketflash.entity.Booking;
import com.barox.ticketflash.enums.BookingStatus;
import com.barox.ticketflash.exception.DataNotFoundException;
import com.barox.ticketflash.repository.BookingRepository;
import com.barox.ticketflash.service.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckInServiceImpl implements CheckInService {

    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public CheckInResponse checkIn(CheckInRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new DataNotFoundException("Booking not found: " + request.getBookingId()));

        if (!booking.getQrToken().equals(request.getQrToken())) {
            throw new IllegalArgumentException("Invalid QR token for booking: " + request.getBookingId());
        }
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new IllegalArgumentException("Booking is not confirmed (status: " + booking.getStatus() + ")");
        }
        if (booking.isCheckedIn()) {
            throw new IllegalArgumentException("Booking already checked in at " + booking.getCheckedInAt());
        }

        booking.setCheckedIn(true);
        booking.setCheckedInAt(LocalDateTime.now());
        bookingRepository.save(booking);

        CheckInResponse response = new CheckInResponse();
        response.setBookingId(booking.getId());
        response.setEmail(booking.getEmail());
        response.setEventName(booking.getEvent().getName());
        response.setCheckedIn(true);
        response.setCheckedInAt(booking.getCheckedInAt());
        return response;
    }
}
