package com.ticketingsystem.TicketingSystem_backend.dto;

public class TicketRequest {
    private String eventName;
    private int ticketsToAdd;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getTicketsToAdd() {
        return ticketsToAdd;
    }

    public void setTicketsToAdd(int ticketsToAdd) {
        this.ticketsToAdd = ticketsToAdd;
    }
}
