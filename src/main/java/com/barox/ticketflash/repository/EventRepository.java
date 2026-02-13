package com.barox.ticketflash.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.barox.ticketflash.entity.Event;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Tạm thời override để giải quyết N+1 query problem
    // Vì hàm này để debug tạm
    // Tương lai sẽ dùng hàm khác với phân trang
    @Override
    @EntityGraph(attributePaths = {"venue", "ticketClasses"})
    public List<Event> findAll();
}
