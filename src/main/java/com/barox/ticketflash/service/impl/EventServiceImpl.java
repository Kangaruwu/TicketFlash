package com.barox.ticketflash.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.barox.ticketflash.repository.EventRepository;
import com.barox.ticketflash.mapper.EventMapper;
import com.barox.ticketflash.repository.VenueRepository;
import com.barox.ticketflash.entity.Venue;
import com.barox.ticketflash.enums.EventStatus;
import com.barox.ticketflash.exception.DataNotFoundException;
import com.barox.ticketflash.service.EventService;
import com.barox.ticketflash.dto.response.EventResponse;
import com.barox.ticketflash.entity.Event;
import com.barox.ticketflash.dto.request.EventRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.stream.Collectors;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final VenueRepository venueRepository;
    // private final CacheManager cacheManager;

    @Override
    @CacheEvict(value = "events", key="'all'")
    public EventResponse createEvent(EventRequest request) throws DataNotFoundException{
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new IllegalArgumentException("Event start time must be before end time.");
        }
        
        // Get Venue
        Long venueId = request.getVenueId();
        Venue venue = venueRepository.findById(venueId)
        .orElseThrow(() -> new DataNotFoundException("Venue not found with ID: " + venueId));
        
        // Check capacity
        int totalTickets = 0;
        for (var ticketClassRequest : request.getTicketClasses()) {
            totalTickets += ticketClassRequest.getQuantityAvailable();
        }
        if (totalTickets > venue.getCapacity()) {
            throw new IllegalArgumentException("Total tickets exceed venue capacity.");
        }
        
        // Create Event
        Event event = eventMapper.toEntity(request);
        event.setVenue(venue);
        event.setStatus(EventStatus.DRAFT);
        
        // Ensure bidirectional association: map ticket classes and set their parent event
        if (event.getTicketClasses() != null) {
            for (var ticketClass : event.getTicketClasses()) {
                ticketClass.setEvent(event);
                if (ticketClass.getQuantitySold() == null) {
                    ticketClass.setQuantitySold(0);
                }
            }
        }

        // Delete cache for events list
        //cacheManager.getCache("events").clear();

        return eventMapper.toResponse(eventRepository.save(event));
    }

    @Override
    @Cacheable(value = "events", key="'all'")
    public List<EventResponse> getAllEvents() {
        // if (cacheManager.getCache("events").get("all") != null) {
        //     return cacheManager.getCache("events").get("all", List.class);
        // }

        List<EventResponse> events = eventRepository.findAll()
                .stream()
                .map(eventMapper::toResponse)
                .collect(Collectors.toList());

        //cacheManager.getCache("events").put("all", events);
        return events;
    }

    @Override
    public List<EventResponse> getEventsByVenueId(Long venueId) {
        return null;
    }
    @Override
    public List<EventResponse> getEventsByStatus(String status) {
        return null;
    }
    @Override
    public List<EventResponse> getEventsByStartTime(String startTime) {
        return null;
    }
    @Override
    @Cacheable(value = "events", key="#id")
    public EventResponse getEventById(Long id) {
        // if (cacheManager.getCache("event").get(id) != null) {
        //     return cacheManager.getCache("event").get(id, EventResponse.class);
        // }
        
        EventResponse eventResponse = eventRepository.findById(id)
                .map(eventMapper::toResponse)
                .orElseThrow(() -> new DataNotFoundException("Event not found with ID: " + id));
        
        //cacheManager.getCache("event").put(id, eventResponse);
        return eventResponse;
    }

}
