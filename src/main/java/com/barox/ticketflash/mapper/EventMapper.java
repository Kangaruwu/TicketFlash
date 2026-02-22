package com.barox.ticketflash.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.barox.ticketflash.dto.response.EventResponse;
import com.barox.ticketflash.dto.request.EventRequest;

import com.barox.ticketflash.entity.Event;

// Dùng "uses" khi mapper có tham chiếu đến các mapper khác
// Nếu Class A CHỨA Class B, thì Mapper A phải uses Mapper B
// Ví dụ: Event CHỨA Venue, nên EventMapper uses VenueMapper
@Mapper(componentModel = "spring", uses = {VenueMapper.class, TicketClassMapper.class})
public interface EventMapper {

    Event toEntity(EventRequest event);

    EventResponse toResponse(Event event);

    void updateEntity(@MappingTarget Event event, EventRequest eventRequest);
    
}
