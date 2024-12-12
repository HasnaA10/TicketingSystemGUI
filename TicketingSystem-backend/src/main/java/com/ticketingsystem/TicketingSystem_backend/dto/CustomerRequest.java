package com.ticketingsystem.TicketingSystem_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerRequest {
    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("eventName")
    private String eventName;

    @JsonProperty( "ticketsPurchased")
    private int ticketsPurchased;

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getTicketsPurchased() {
        return ticketsPurchased;
    }

    public void setTicketsPurchased(int ticketsPurchased) {
        this.ticketsPurchased = ticketsPurchased;
    }
}
