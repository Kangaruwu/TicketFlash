package com.barox.ticketflash.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CheckInResponse {

    private UUID bookingId;
    private String email;
    private String eventName;
    private boolean checkedIn;
    private LocalDateTime checkedInAt;
}
