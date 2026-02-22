package com.barox.ticketflash.dto.request;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class TicketBookingRequest {
    
    private Long ticketId;
    private Integer quantity;
    
}
