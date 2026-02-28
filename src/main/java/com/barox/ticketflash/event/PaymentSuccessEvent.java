package com.barox.ticketflash.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentSuccessEvent {
    private String email;
    private String eventName;
    private Integer totalTicket;
}
