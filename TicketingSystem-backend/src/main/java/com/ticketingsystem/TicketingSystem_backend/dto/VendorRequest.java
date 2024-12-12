package com.ticketingsystem.TicketingSystem_backend.dto;

public class VendorRequest {
    private String vendorName;
    private String eventName;
    private int ticketsToRelease;

    public VendorRequest(String vendorName, String eventName, int ticketsToRelease) {
        this.vendorName = vendorName;
        this.eventName = eventName;
        this.ticketsToRelease = ticketsToRelease;
    }

    public String getVendorName() {
        return vendorName;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getTicketsToRelease() {
        return ticketsToRelease;
    }
}
