package com.barox.ticketflash.service.impl;

import org.springframework.stereotype.Service;
import com.barox.ticketflash.service.VenueService;
import lombok.RequiredArgsConstructor;
import com.barox.ticketflash.repository.VenueRepository;
import com.barox.ticketflash.dto.request.VenueRequest;
import com.barox.ticketflash.dto.response.VenueResponse;
import com.barox.ticketflash.entity.Venue;
import com.barox.ticketflash.mapper.VenueMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {
    
    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    @Override
    public VenueResponse createVenue(VenueRequest request) {
        Venue venue = venueMapper.toEntity(request);
        Venue savedVenue = venueRepository.save(venue);
        return venueMapper.toResponse(savedVenue);
    }

    @Override
    public List<VenueResponse> getAllVenues() {
        return venueRepository.findAll()
                .stream()
                .map(venueMapper::toResponse)
                .collect(Collectors.toList());
    }
}
