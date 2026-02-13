package com.barox.ticketflash.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.barox.ticketflash.dto.request.TicketClassRequest;
import com.barox.ticketflash.dto.response.TicketClassResponse;
import com.barox.ticketflash.entity.TicketClass;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketClassMapper {
    
    TicketClass toEntity(TicketClassRequest request);

    // Chỉ lấy eventId từ event để tránh stack overflow
    @Mapping(target = "eventId", source = "event.id")
    TicketClassResponse toResponse(TicketClass ticketClass);

    void updateEntity(@MappingTarget TicketClass ticketClass, TicketClassRequest request);

}
