//package com.ticketingsystem.TicketingSystem_backend.repository;
//
//import com.ticketingsystem.TicketingSystem_backend.model.Ticket;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface TicketRepository extends JpaRepository<Ticket, Long> {
//    List<Ticket> findByEventNameAndSoldFalse(String eventName);
//}

package com.ticketingsystem.TicketingSystem_backend.repository;

import com.ticketingsystem.TicketingSystem_backend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEventNameAndSoldFalse(String eventName);

    int countByEventName(String eventName);

    int countByEventNameAndSoldTrue(String eventName);
}

