package com.barox.ticketflash.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.barox.ticketflash.entity.TicketClass;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;


@Repository
public interface TicketClassRepository extends JpaRepository<TicketClass, Long> {
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT tc FROM TicketClass tc WHERE tc.id = :id")
    public TicketClass findByIdWithLock(Long id);

}
