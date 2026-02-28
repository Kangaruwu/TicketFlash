package com.barox.ticketflash.dto.response;

import lombok.Data;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Data
public class BookingResponse {
    
    private UUID id;
    private String eventName;
    private Long eventId;
    private String email;
    private String status;
    private Integer totalTicket;
    private LocalDateTime bookingDate;
    private List<TicketBookingResponse> ticketDetails;

}
