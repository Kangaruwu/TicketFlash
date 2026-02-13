package com.barox.ticketflash.mapper;

import com.barox.ticketflash.entity.Venue;
import com.barox.ticketflash.dto.request.VenueRequest;
import com.barox.ticketflash.dto.response.VenueResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VenueMapper {
    
    // Convert from VenueRequest to Venue entity
    Venue toEntity(VenueRequest request);

    // Convert from Venue entity to VenueResponse
    VenueResponse toResponse(Venue venue);

    // Update existing Venue entity with data from VenueRequest
    void updateEntity(@MappingTarget Venue venue, VenueRequest request);

}
