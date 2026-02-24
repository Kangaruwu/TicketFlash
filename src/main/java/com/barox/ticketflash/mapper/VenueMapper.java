package com.barox.ticketflash.mapper;

import com.barox.ticketflash.entity.Venue;
import com.barox.ticketflash.dto.request.VenueRequest;
import com.barox.ticketflash.dto.response.VenueResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VenueMapper {
    
    // Convert from VenueRequest to Venue entity
    @Mapping(target = "id", ignore = true) // Ignore ID when creating a new entity
    @Mapping(target = "events", ignore = true) // Ignore events to prevent circular mapping
    Venue toEntity(VenueRequest request);

    // Convert from Venue entity to VenueResponse
    VenueResponse toResponse(Venue venue);

    // Update existing Venue entity with data from VenueRequest
    @Mapping(target = "id", ignore = true) // Ignore ID when updating
    @Mapping(target = "events", ignore = true) // Ignore events to prevent circular mapping
    void updateEntity(@MappingTarget Venue venue, VenueRequest request);

}
