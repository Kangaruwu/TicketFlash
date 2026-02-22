package com.barox.ticketflash.dto.response;
import lombok.Data;

@Data
public class TicketClassResponse {
    private Long id;
    private String name;
    private java.math.BigDecimal price;
    private Integer quantityAvailable;
    private Integer quantitySold;
    //private EventResponse event;   CON MÀ CHỨA LẠI NGƯỢC CHA LÀ STACKOVERFLOW
    private Long eventId;
}
