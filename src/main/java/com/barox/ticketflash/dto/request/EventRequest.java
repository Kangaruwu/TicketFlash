package com.barox.ticketflash.dto.request;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class EventRequest {

    @NotNull(message = "Event name is required")
    private String name;

    private String description;

    @NotNull(message = "Event start time is required")
    @Future(message = "Event start time must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "Event end time is required")
    @Future(message = "Event end time must be in the future")
    private LocalDateTime endTime;

    @NotNull(message = "Venue ID is required")
    private Long venueId;

    @NotNull(message = "Ticket classes are required")
    private List<TicketClassRequest> ticketClasses;
}
