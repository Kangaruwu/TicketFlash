package com.barox.ticketflash.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketBookingResponse {

    private Long id;
    private Integer quantity;
    private String ticketClassName;

}
