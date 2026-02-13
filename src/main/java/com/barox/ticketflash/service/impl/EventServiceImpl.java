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

import java.util.stream.Collectors;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final VenueRepository venueRepository;

    @Override
    public EventResponse createEvent(EventRequest request) throws DataNotFoundException{
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new IllegalArgumentException("Event start time must be before end time.");
        }
        
        Long venueId = request.getVenueId();
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new DataNotFoundException("Venue not found with ID: " + venueId));

        Event event = eventMapper.toEntity(request);
        event.setVenue(venue);
        event.setStatus(EventStatus.DRAFT);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toResponse(savedEvent);
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toResponse)
                .collect(Collectors.toList());
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
    public EventResponse getEventById(Long id) {
        return null;
    }

}
