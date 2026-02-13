package com.barox.ticketflash.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.barox.ticketflash.entity.TicketClass;

@Repository
public interface TicketClassRepository extends JpaRepository<TicketClass, Long> {
    
}
