package com.barox.ticketflash.service;

import com.barox.ticketflash.dto.request.VenueRequest;
import com.barox.ticketflash.dto.response.VenueResponse;
import java.util.List;

public interface VenueService {
    VenueResponse createVenue(VenueRequest request);
    List<VenueResponse> getAllVenues();
}
