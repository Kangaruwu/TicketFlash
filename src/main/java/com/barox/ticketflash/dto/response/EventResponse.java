package com.barox.ticketflash.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventResponse {

    private Long id;
    
    private String name;
    
    private String description;

    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private String status;
    
    private VenueResponse venue;

}
