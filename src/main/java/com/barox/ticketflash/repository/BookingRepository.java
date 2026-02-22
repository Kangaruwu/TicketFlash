package com.barox.ticketflash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.barox.ticketflash.entity.Booking;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    
}
