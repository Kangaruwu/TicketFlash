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
import com.querydsl.core.BooleanBuilder;
import com.barox.ticketflash.dto.response.EventResponse;
import com.barox.ticketflash.dto.response.PagedResponse;
import com.barox.ticketflash.entity.Event;
import com.barox.ticketflash.entity.QEvent;
import com.barox.ticketflash.dto.request.EventRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    @Cacheable(value = "events", key = "#page + '-' + #size + '-' + #sortBy + '-' + #sortDir")    
    public PagedResponse<EventResponse> getAllEvents(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? 
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Event> eventPage = eventRepository.findAll(pageable);

        List<EventResponse> events = eventPage.getContent().stream()
                .map(eventMapper::toResponse)
                .collect(Collectors.toList());

        return PagedResponse.<EventResponse>builder()
                .content(events)
                .pageNumber(page)
                .pageSize(size)
                .totalElements(eventPage.getTotalElements())
                .totalPages(eventPage.getTotalPages())
                .last(eventPage.isLast())
                .build();
    }

    @Override
    public List<EventResponse> getEventsByVenueId(Long venueId) {
        return null;
    }
    @Override
    public List<EventResponse> getEventsByStatus(EventStatus status) {
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

    @Override
    public PagedResponse<EventResponse> searchEvents(String name, EventStatus status, int page, int size, String sortBy, String sortDir) {
        QEvent qEvent = QEvent.event;
        BooleanBuilder predicate = new BooleanBuilder();

        if (name != null && !name.isEmpty()) {
            predicate.and(qEvent.name.containsIgnoreCase(name)); // Dịch ra: LIKE %name%
        }

        if (status != null) {
            predicate.and(qEvent.status.eq(status)); // Dịch ra: = status
        }

        Sort sort = sortDir.equalsIgnoreCase("asc") ? 
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Event> eventPage = eventRepository.findAll(predicate.getValue(), pageable);

        List<EventResponse> events = eventPage.getContent().stream()
                .map(eventMapper::toResponse)
                .collect(Collectors.toList());

        return PagedResponse.<EventResponse>builder()
                .content(events)
                .pageNumber(page)
                .pageSize(size)
                .totalElements(eventPage.getTotalElements())
                .totalPages(eventPage.getTotalPages())
                .last(eventPage.isLast())
                .build();
    }
}
