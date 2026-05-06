package com.barox.ticketflash.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckInRequest {

    @NotNull(message = "bookingId is required")
    private UUID bookingId;

    @NotNull(message = "qrToken is required")
    private UUID qrToken;
}
