package com.barox.ticketflash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.barox.ticketflash.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
    
}
