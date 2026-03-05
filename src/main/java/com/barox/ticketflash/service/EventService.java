package com.barox.ticketflash.service;

import com.barox.ticketflash.dto.request.EventRequest;
import com.barox.ticketflash.dto.response.EventResponse;
import com.barox.ticketflash.dto.response.PagedResponse;
import java.util.List;

public interface EventService {
    EventResponse createEvent(EventRequest request);
    PagedResponse<EventResponse> getAllEvents(int page, int size, String sortBy, String sortDir);
    List<EventResponse> getEventsByVenueId(Long venueId);
    List<EventResponse> getEventsByStatus(String status);
    List<EventResponse> getEventsByStartTime(String startTime);
    EventResponse getEventById(Long id);

}
