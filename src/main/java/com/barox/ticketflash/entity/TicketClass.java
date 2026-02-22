package com.barox.ticketflash.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import lombok.*;

@Entity
@Table(name = "ticket_classes", indexes = {
    @Index(name = "idx_ticket_class_event_id", columnList = "event_id"),
    @Index(name = "idx_ticket_class_name", columnList = "name"),
    @Index(name = "idx_ticket_class_booking_id", columnList = "booking_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketClass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable;

    @Column(name = "quantity_sold", nullable = false)
    private Integer quantitySold;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude           // <--- QUAN TRỌNG NHẤT: Ngắt vòng lặp toString
    @EqualsAndHashCode.Exclude  // <--- NÊN CÓ: Ngắt vòng lặp khi tính HashCode
    @JsonIgnore                 // <--- NÊN CÓ: Để khi trả JSON không bị lặp vô tận
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "booking_id", nullable = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Booking booking;
}
