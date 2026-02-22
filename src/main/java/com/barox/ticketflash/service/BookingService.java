package com.barox.ticketflash.service;

import org.springframework.stereotype.Service;

import com.barox.ticketflash.dto.request.BookingRequest;
import com.barox.ticketflash.dto.response.BookingResponse;
import com.barox.ticketflash.security.CustomUserDetails;

@Service
public interface BookingService {

    public BookingResponse bookTickets(BookingRequest bookingRequest, CustomUserDetails userDetails);
        
}
