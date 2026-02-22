package com.barox.ticketflash.service;

import org.springframework.stereotype.Service;

import com.barox.ticketflash.dto.request.BookingRequest;
import com.barox.ticketflash.dto.response.BookingResponse;
import com.barox.ticketflash.security.CustomUserDetails;
import java.util.List;

@Service
public interface BookingService {

    public BookingResponse bookTickets(BookingRequest bookingRequest, CustomUserDetails userDetails);
    public List<BookingResponse> myBookings(CustomUserDetails userDetails);
    
}
