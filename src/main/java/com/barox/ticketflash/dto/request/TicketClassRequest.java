package com.barox.ticketflash.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketClassRequest {
    @NotNull(message = "Ticket class name is required")
    private String name;

    @NotNull(message = "Ticket class price is required")
    @Min(value = 1, message = "Price must be at least 1")
    private java.math.BigDecimal price;

    @NotNull(message = "Ticket class quantity available is required")
    @Min(value = 1, message = "Quantity available must be at least 1")
    private Integer quantityAvailable;
}
