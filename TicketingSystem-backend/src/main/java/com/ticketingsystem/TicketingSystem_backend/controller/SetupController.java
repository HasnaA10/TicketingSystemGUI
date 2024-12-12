package com.ticketingsystem.TicketingSystem_backend.controller;

import com.ticketingsystem.TicketingSystem_backend.dto.*;
import com.ticketingsystem.TicketingSystem_backend.Service.TicketSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*")
public class SetupController {

    @Autowired
    private TicketSystemService ticketSystemService;

    @PostMapping("/add")
    public ResponseEntity<String> addConfiguration(@RequestBody SetupRequest setupRequest) {
        try {
            ticketSystemService.setupConfiguration(setupRequest);
            return ResponseEntity.ok("Configuration added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to configure ticketing system: " + e.getMessage());
        }
    }

    // add a new vendor
    @PostMapping("/vendor")
    public ResponseEntity<String> createVendor(@RequestBody VendorRequest vendorRequest) {
        try {
            ticketSystemService.saveVendorAndEvent(
                    vendorRequest.getVendorName(),
                    vendorRequest.getEventName(),
                    vendorRequest.getTicketsToRelease()
            );
            return ResponseEntity.ok("Vendor and tickets added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to add vendor and tickets: " + e.getMessage());
        }
    }

    @PostMapping("/addtickets")
    public ResponseEntity<String> addTickets(@RequestParam String eventName, @RequestParam int ticketsToRelease) {
        try {
            ticketSystemService.addTickets(eventName, ticketsToRelease); // Add tickets to the system
            return ResponseEntity.ok("Tickets added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to add tickets: " + e.getMessage());
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<String> addCustomer(@RequestBody CustomerRequest customerRequest) {
        try {
            // Check if customerName and eventName are not null
            if (customerRequest.getCustomerName() == null || customerRequest.getEventName() == null) {
                return ResponseEntity.badRequest().body("Customer name and event name are required.");
            }

            boolean isUpdated = ticketSystemService.saveCustomerAndEvent(customerRequest.getCustomerName(),
                    customerRequest.getEventName(), customerRequest.getTicketsPurchased());

            if (isUpdated) {
                return ResponseEntity.ok("Customer updated successfully!");
            } else {
                return ResponseEntity.ok("Customer added successfully!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to add customer: " + e.getMessage());
        }
    }
    @PostMapping("/purchase")
        public ResponseEntity<String> purchaseTickets(@RequestParam String customerName, @RequestParam String eventName, @RequestParam int ticketsToPurchase) {
        try {
            ticketSystemService.purchaseTickets(customerName, eventName, ticketsToPurchase);
            return ResponseEntity.ok("Tickets purchased successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to purchase tickets: " + e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<List<TicketStatusResponse>> getTicketStatus() {
        try {
            List<TicketStatusResponse> ticketStatuses = ticketSystemService.getTicketStatusForAllEvents();
            return ResponseEntity.ok(ticketStatuses);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}