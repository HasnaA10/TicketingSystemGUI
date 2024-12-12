package com.ticketingsystem.TicketingSystem_backend.dto;

public class TicketStatusResponse {
    private String eventName;
    private int availableTickets;
    private int soldTickets;

    public TicketStatusResponse(String eventName, int availableTickets, int soldTickets) {
        this.eventName = eventName;
        this.availableTickets = availableTickets;
        this.soldTickets = soldTickets;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public int getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }
}
