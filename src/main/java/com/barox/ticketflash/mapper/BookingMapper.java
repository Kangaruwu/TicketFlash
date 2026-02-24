package com.barox.ticketflash.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.barox.ticketflash.dto.response.BookingResponse;
import com.barox.ticketflash.dto.response.TicketBookingResponse;
import com.barox.ticketflash.dto.request.BookingRequest;
import com.barox.ticketflash.entity.Booking;
import com.barox.ticketflash.entity.BookingDetails;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)          // event được set bởi service
    @Mapping(target = "bookingDetails", ignore = true) // bookingDetails được set bởi service
    @Mapping(target = "email", ignore = true)          // email được lấy từ authenticated user
    @Mapping(target = "status", ignore = true)         // status được set bởi service
    @Mapping(target = "totalTicket", ignore = true)    // tính toán bởi service
    @Mapping(target = "bookingDate", ignore = true)    // set bởi service
    Booking toEntity(BookingRequest request);

    @Mapping(source = "event.name", target = "eventName")
    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "bookingDetails", target = "ticketDetails")
    BookingResponse toResponse(Booking booking);

    @Mapping(source = "ticketClass.name", target = "ticketClassName")
    TicketBookingResponse toTicketBookingResponse(BookingDetails bookingDetails);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)          // email không cập nhật qua request
    @Mapping(target = "status", ignore = true)         // status không cập nhật qua request
    @Mapping(target = "totalTicket", ignore = true)    // tính toán bởi service
    @Mapping(target = "bookingDate", ignore = true)    // không cập nhật qua request
    @Mapping(target = "event", ignore = true)          // event không cập nhật qua request
    @Mapping(target = "bookingDetails", ignore = true) // bookingDetails không cập nhật qua request
    void updateEntity(@MappingTarget Booking booking, BookingRequest request);

}
