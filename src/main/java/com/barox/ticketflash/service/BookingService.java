package com.barox.ticketflash.service;

import org.springframework.stereotype.Service;

import com.barox.ticketflash.dto.request.BookingRequest;
import com.barox.ticketflash.dto.response.BookingResponse;

@Service
public interface BookingService {

    public BookingResponse bookTickets(BookingRequest bookingRequest);
        
}
