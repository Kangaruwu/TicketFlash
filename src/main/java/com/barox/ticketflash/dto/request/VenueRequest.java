package com.barox.ticketflash.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class VenueRequest {
    @NotNull(message = "Venue name is required")
    private String name;

    @NotNull(message = "Venue address is required")
    private String address;

    @Min(value = 1, message = "Venue capacity must be at least 1")
    private Integer capacity;
}
