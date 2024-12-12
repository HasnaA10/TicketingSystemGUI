package com.ticketingsystem.TicketingSystem_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "vendor_name", nullable = false, unique = true)
    private String vendorName;

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "name")
    private String name;
    public Long getId() {
        return vendorId;
    }

    public void setId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return vendorName;
    }

    public void setName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
