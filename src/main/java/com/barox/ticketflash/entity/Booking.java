package com.barox.ticketflash.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.UUID;
import java.time.LocalDateTime;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;
import com.barox.ticketflash.enums.BookingStatus;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bookings", indexes = {
    @Index(name = "idx_booking_email", columnList = "email"),
    @Index(name = "idx_booking_status", columnList = "status"),
    @Index(name = "idx_booking_booking_date", columnList = "booking_date"),
    @Index(name = "idx_booking_event_id", columnList = "event_id")
})
public class Booking {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;

    @Column(name = "total_ticket", nullable = false)
    private Integer totalTicket;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false) //Thể hiện ràng buộc Total Participation (Booking bắt buộc có Event)
    private Event event;

    @Column(name = "qr_token", nullable = false, unique = true, updatable = false)
    private UUID qrToken;

    @Column(name = "checked_in", nullable = false)
    private boolean checkedIn = false;

    @Column(name = "checked_in_at")
    private LocalDateTime checkedInAt;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<BookingDetails> bookingDetails;

    public void addBookingDetails(BookingDetails bookingDetail) {
        if (this.bookingDetails == null) {
            this.bookingDetails = new java.util.ArrayList<>();
        }
        bookingDetails.add(bookingDetail);
        bookingDetail.setBooking(this);
    }

}
