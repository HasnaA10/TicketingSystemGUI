package com.ticketingsystem.TicketingSystem_backend.repository;

import com.ticketingsystem.TicketingSystem_backend.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepo extends JpaRepository<Configuration, Long> {
    Configuration findTopByOrderByIdDesc();
}
