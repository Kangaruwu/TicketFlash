package com.barox.ticketflash.dto.response;

import lombok.Data;

@Data
public class TicketBookingResponse {

    private Long id;
    private Integer quantity;
    private String ticketClassName;

}
