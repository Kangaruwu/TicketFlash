package com.barox.ticketflash.mapper;

import org.hibernate.boot.internal.Target;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.barox.ticketflash.dto.response.BookingResponse;
import com.barox.ticketflash.dto.request.BookingRequest;
import com.barox.ticketflash.entity.Booking;

@Mapper(componentModel = "spring", uses = {TicketClassMapper.class})
public interface BookingMapper {
    
    Booking toEntity(BookingRequest request);

    BookingResponse toResponse(Booking booking);

    void updateEntity(@MappingTarget Booking booking, BookingRequest request);

}
