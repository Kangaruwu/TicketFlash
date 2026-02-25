package com.barox.ticketflash.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookingSuccessEvent {
    private String email;
    private String eventName;
    private Integer totalTicket;
}