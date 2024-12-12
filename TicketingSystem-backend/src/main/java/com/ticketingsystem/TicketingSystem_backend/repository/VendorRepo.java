//package com.ticketingsystem.TicketingSystem_backend.repository;
//
//import com.ticketingsystem.TicketingSystem_backend.model.Vendor;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface VendorRepo extends JpaRepository<Vendor, Long> {
//}

package com.ticketingsystem.TicketingSystem_backend.repository;

import com.ticketingsystem.TicketingSystem_backend.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepo extends JpaRepository<Vendor, Long> {
    public Vendor findByName(String vendorName);
}
