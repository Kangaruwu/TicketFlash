package com.barox.ticketflash.dto.response;

import lombok.Data;

@Data
public class VenueResponse {
    private Long id;
    private String name;
    private String address;
    private Integer capacity;
}
