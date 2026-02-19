package com.barox.ticketflash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.barox.ticketflash.entity.User;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
}
