package com.ticketingsystem.TicketingSystem_backend.model;

import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false, unique = true)
    private String customerName;
    @Column(nullable = false)
    private String eventName;
    @Column(nullable = false)
    private int ticketsPurchased;

    // Getters and Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public int getTicketsPurchased() {
        return ticketsPurchased;
    }

    public void setTicketsPurchased(int ticketsPurchased) {
        this.ticketsPurchased = ticketsPurchased;
    }
}
