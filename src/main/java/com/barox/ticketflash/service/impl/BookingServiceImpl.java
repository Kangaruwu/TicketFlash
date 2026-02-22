package com.barox.ticketflash.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.barox.ticketflash.dto.request.BookingRequest;
import com.barox.ticketflash.dto.response.BookingResponse;
import com.barox.ticketflash.dto.request.TicketBookingRequest;
import com.barox.ticketflash.dto.response.TicketBookingResponse;
import com.barox.ticketflash.repository.EventRepository;
import com.barox.ticketflash.repository.TicketClassRepository;
import com.barox.ticketflash.security.CustomUserDetails;
import com.barox.ticketflash.service.BookingService;
import com.barox.ticketflash.entity.Event;
import com.barox.ticketflash.entity.TicketClass;
import com.barox.ticketflash.enums.BookingStatus;
import com.barox.ticketflash.exception.DataNotFoundException;
import com.barox.ticketflash.entity.Booking;
import com.barox.ticketflash.entity.BookingDetails;
import com.barox.ticketflash.mapper.BookingMapper;
import com.barox.ticketflash.repository.BookingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final TicketClassRepository ticketClassRepository;
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional
    public BookingResponse bookTickets(BookingRequest bookingRequest, CustomUserDetails userDetails) {
        // Validate event exists
        Event event = eventRepository.findById(bookingRequest.getEventId())
            .orElseThrow(() -> new DataNotFoundException("Event not found with ID: " + bookingRequest.getEventId()));

        List<TicketBookingRequest> ticketDetails = bookingRequest.getTicketDetails();
        List<TicketBookingResponse> ticketBookingResponses = new ArrayList<>();
        List<TicketClass> bookedTicketClasses = new ArrayList<>();
        int totalTickets = 0;

        Booking booking = Booking.builder()
            .email(userDetails.getEmail())
            .status(BookingStatus.CONFIRMED)
            .totalTicket(totalTickets)
            .bookingDate(LocalDateTime.now())
            .event(event)
            .build();

        // Sort id vé trong request trước vì có khi người gửi lên mua vé 1, 2
        // có người mua vé 2,1 thì bị deadlock
        ticketDetails.sort((t1, t2) -> t1.getTicketId().compareTo(t2.getTicketId()));

        for (TicketBookingRequest ticketBookingRequest : ticketDetails) {
            Long ticketClassId = ticketBookingRequest.getTicketId();
            Integer quantity = ticketBookingRequest.getQuantity();

            // Fetch the Event with PESSIMISTIC_WRITE lock
            TicketClass ticketClass = ticketClassRepository.findByIdWithLock(ticketClassId);
            if (ticketClass == null) {
                throw new IllegalArgumentException("Ticket class not found with ID: " + ticketClassId);
            }

            // Check if enough tickets are available
            if (ticketClass.getQuantityAvailable() < quantity) {
                throw new IllegalArgumentException("Not enough tickets available for ticket class ID: " + ticketClassId);
            }

            // Update quantities: decrease available, increase sold
            ticketClass.setQuantityAvailable(ticketClass.getQuantityAvailable() - quantity);
            ticketClass.setQuantitySold(ticketClass.getQuantitySold() + quantity);
            ticketClassRepository.save(ticketClass);
            
            bookedTicketClasses.add(ticketClass);
            ticketBookingResponses.add(
                new TicketBookingResponse(ticketClass.getId(), quantity, ticketClass.getName())
            );
            
            booking.addBookingDetails(
                BookingDetails.builder()
                    .ticketClass(ticketClass)
                    .quantity(quantity)
                    .price(ticketClass.getPrice())
                    .build()
            );

            totalTickets += quantity;
        }

        // Store booking information in database
        bookingRepository.save(booking);

        BookingResponse response = bookingMapper.toResponse(booking);
        response.setTicketDetails(ticketBookingResponses);
        return response;
    }

    @Override
    public List<BookingResponse> myBookings(CustomUserDetails userDetails) {
        return null;
    }
}


