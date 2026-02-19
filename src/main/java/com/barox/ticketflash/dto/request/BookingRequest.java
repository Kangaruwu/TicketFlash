package com.barox.ticketflash.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class BookingRequest {
    private Long eventId;
    private List<TicketBookingRequest> ticketDetails;
}
