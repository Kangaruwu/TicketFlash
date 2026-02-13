package com.barox.ticketflash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.barox.ticketflash.entity.Event;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
