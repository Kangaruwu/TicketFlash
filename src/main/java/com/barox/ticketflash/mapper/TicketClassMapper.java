package com.barox.ticketflash.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.barox.ticketflash.dto.request.TicketClassRequest;
import com.barox.ticketflash.dto.response.TicketClassResponse;
import com.barox.ticketflash.entity.TicketClass;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketClassMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "quantitySold", constant = "0")
    TicketClass toEntity(TicketClassRequest request);

    // Chỉ lấy eventId từ event để tránh stack overflow
    @Mapping(target = "eventId", source = "event.id")
    TicketClassResponse toResponse(TicketClass ticketClass);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "quantitySold", ignore = true)
    void updateEntity(@MappingTarget TicketClass ticketClass, TicketClassRequest request);

}
