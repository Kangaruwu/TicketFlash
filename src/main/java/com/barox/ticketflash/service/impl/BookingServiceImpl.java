package com.barox.ticketflash.service.impl;

import java.security.Security;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barox.ticketflash.dto.request.BookingRequest;
import com.barox.ticketflash.dto.response.BookingResponse;
import com.barox.ticketflash.mapper.TicketClassMapper;
import com.barox.ticketflash.repository.EventRepository;
import com.barox.ticketflash.repository.TicketClassRepository;
import com.barox.ticketflash.security.CustomUserDetails;
import com.barox.ticketflash.service.BookingService;
import com.barox.ticketflash.dto.request.TicketBookingRequest;
import com.barox.ticketflash.entity.Event;
import com.barox.ticketflash.entity.TicketClass;
import com.barox.ticketflash.enums.BookingStatus;
import com.barox.ticketflash.exception.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final TicketClassRepository ticketClassRepository;
    private final EventRepository eventRepository;
    private final TicketClassMapper ticketClassMapper;

    @Override
    @Transactional
    public BookingResponse bookTickets(BookingRequest bookingRequest, CustomUserDetails userDetails) {
        // Validate event exists
        Event event = eventRepository.findById(bookingRequest.getEventId())
            .orElseThrow(() -> new DataNotFoundException("Event not found with ID: " + bookingRequest.getEventId()));

        List<TicketBookingRequest> ticketDetails = bookingRequest.getTicketDetails();
        List<TicketClass> bookedTicketClasses = new ArrayList<>();
        int totalTickets = 0;

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
            totalTickets += quantity;
        }

        // Response mapping and return
        BookingResponse response = new BookingResponse();
        response.setEventId(event.getId());
        response.setEventName(event.getName());
        response.setEmail(userDetails.getEmail());
        response.setStatus(BookingStatus.CONFIRMED.name());
        response.setTotalTicket(totalTickets);
        response.setBookingDate(LocalDateTime.now());
        response.setTicketClasses(
            bookedTicketClasses.stream()
                .map(ticketClassMapper::toResponse)
                .collect(Collectors.toList())
        );
        
        return response;
    }
}


