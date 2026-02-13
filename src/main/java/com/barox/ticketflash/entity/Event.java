package com.barox.ticketflash.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import com.barox.ticketflash.enums.EventStatus; 

@Entity
@Table(name = "events", indexes = {
    @Index(name = "idx_event_status", columnList = "status"),
    @Index(name = "idx_event_start_time", columnList = "start_time")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venue_id", nullable = false) // nullable = false: Thể hiện ràng buộc Total Participation (Event bắt buộc có Venue)
    private Venue venue;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketClass> ticketClasses;

    // Giải quyết vấn đề khi save event thì không có event_id để liên kết với TicketClass
    public void addTicketClass(TicketClass ticketClass) {
        if (this.ticketClasses == null) {
            this.ticketClasses = new java.util.ArrayList<>();
        }
        ticketClasses.add(ticketClass);
        ticketClass.setEvent(this); // Mấu chốt
    }

    public String toStringDetailed() {
        // Print full details for debugging
        StringBuilder sb = new StringBuilder();
        sb.append("Event{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", status=").append(status);
        sb.append(", venue=").append(venue != null ? venue.getName() : "null");
        sb.append(", ticketClasses=[");
        if (ticketClasses != null) {
            for (TicketClass tc : ticketClasses) {
                sb.append(tc.getName()).append(", ");
                sb.append(tc.getPrice()).append(", ");
                sb.append(tc.getQuantityAvailable()).append(", ");
                sb.append(tc.getQuantitySold()).append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}
