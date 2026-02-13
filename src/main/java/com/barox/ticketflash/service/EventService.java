package com.barox.ticketflash.service;

import com.barox.ticketflash.dto.request.EventRequest;
import com.barox.ticketflash.dto.response.EventResponse;
import java.util.List;

public interface EventService {
    EventResponse createEvent(EventRequest request);
    List<EventResponse> getAllEvents();
    List<EventResponse> getEventsByVenueId(Long venueId);
    List<EventResponse> getEventsByStatus(String status);
    List<EventResponse> getEventsByStartTime(String startTime);
    EventResponse getEventById(Long id);

}
