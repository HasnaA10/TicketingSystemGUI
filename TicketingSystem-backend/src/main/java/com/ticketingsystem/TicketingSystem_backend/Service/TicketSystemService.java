package com.ticketingsystem.TicketingSystem_backend.Service;

import com.ticketingsystem.TicketingSystem_backend.dto.SetupRequest;
import com.ticketingsystem.TicketingSystem_backend.dto.TicketStatusResponse;
import com.ticketingsystem.TicketingSystem_backend.model.Configuration;
import com.ticketingsystem.TicketingSystem_backend.model.Customer;
import com.ticketingsystem.TicketingSystem_backend.model.Ticket;
import com.ticketingsystem.TicketingSystem_backend.model.Vendor;
import com.ticketingsystem.TicketingSystem_backend.repository.ConfigurationRepo;
import com.ticketingsystem.TicketingSystem_backend.repository.CustomerRepo;
import com.ticketingsystem.TicketingSystem_backend.repository.TicketRepository;
import com.ticketingsystem.TicketingSystem_backend.repository.VendorRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class TicketSystemService {

    @Autowired
    private ConfigurationRepo configurationRepo;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private VendorRepo vendorRepo;

    public void setupConfiguration(SetupRequest setupRequest) {
        //Validate the configuration parameters.
        if (setupRequest.getMaximumTicketCapacity() <= 0) {
            throw new IllegalArgumentException("Maximum Ticket Capacity must be positive.");
        }
        if (setupRequest.getTotalTickets() <= 0) {
            throw new IllegalArgumentException("Total Tickets must be positive.");
        }
        if (setupRequest.getTicketReleaseRate() <= 0) {
            throw new IllegalArgumentException("Ticket Release Rate must be positive.");
        }
        if (setupRequest.getCustomerRetrievalRate() <= 0) {
            throw new IllegalArgumentException("Customer Retrieval Rate must be positive.");
        }

        Configuration configuration = new Configuration();
        configuration.setMaximumTicketCapacity(setupRequest.getMaximumTicketCapacity());
        configuration.setTotalTickets(setupRequest.getTotalTickets());
        configuration.setTicketReleaseRate(setupRequest.getTicketReleaseRate());
        configuration.setCustomerRetrievalRate(setupRequest.getCustomerRetrievalRate());

        configurationRepo.save(configuration);

        System.out.println("Configuration Saved:");
        System.out.println("Maximum Ticket Capacity: " + configuration.getMaximumTicketCapacity());
        System.out.println("Total Tickets: " + configuration.getTotalTickets());
        System.out.println("Ticket Release Rate: " + configuration.getTicketReleaseRate());
        System.out.println("Customer Retrieval Rate: " + configuration.getCustomerRetrievalRate());
    }

    private static final List<String> tickets = Collections.synchronizedList(new LinkedList<>());

    @Transactional
    public synchronized void purchaseTickets(String customerName, String eventName, int ticketsToPurchase) {

        int customerRetrievalRate = getCustomerRetrievalRate();


        if (ticketsToPurchase > customerRetrievalRate) {
            throw new IllegalArgumentException("Tickets to purchase cannot exceed the customer retrieval rate");
        }

        // Fetch available tickets for the given event where isSold is false
        List<Ticket> availableTickets = ticketRepository.findByEventNameAndSoldFalse(eventName);

        if (availableTickets.size() < ticketsToPurchase) {
            throw new IllegalArgumentException("Not enough tickets available for this event");
        }


        synchronized (this) {
            int ticketsPurchased = 0;

            for (int i = 0; i < ticketsToPurchase; i++) {
                Ticket ticket = availableTickets.get(i);
                ticket.setSold(true);
                ticketRepository.save(ticket);
                ticketsPurchased++;
            }


            if (ticketsPurchased == ticketsToPurchase) {
                System.out.println(ticketsPurchased + " tickets purchased successfully for event: " + eventName);
            }
        }
    }


    private int getCustomerRetrievalRate() {
        Configuration latestConfiguration = configurationRepo.findTopByOrderByIdDesc();
        if (latestConfiguration != null) {
            return latestConfiguration.getCustomerRetrievalRate();
        } else {
            throw new RuntimeException("Configuration not found");
        }
    }


    public void saveVendorAndEvent(String vendorName, String eventName, int ticketsToRelease) {
        Vendor vendor = new Vendor();
        vendor.setName(vendorName);
        vendor.setEventName(eventName);

        vendorRepo.save(vendor);


        try {
            addTickets(eventName, ticketsToRelease);
        } catch (Exception e) {
            System.out.println("Error adding tickets: " + e.getMessage());
        }
    }

    @Transactional
    public void addTickets(String eventName, int ticketsToRelease) {
        for (int i = 0; i < ticketsToRelease; i++) {
            Ticket ticket = new Ticket();
            ticket.setEventName(eventName);
            ticket.setSold(false);
            ticket.setTicketId(String.valueOf(System.currentTimeMillis() + i));

            ticketRepository.save(ticket);
        }
    }

    @Autowired
    private CustomerRepo customerRepo;


    public boolean saveCustomerAndEvent(String customerName, String eventName, int ticketsToPurchase) {
        Customer existingCustomer = customerRepo.findByCustomerNameAndEventName(customerName, eventName);

        if (existingCustomer != null) {
            System.out.println("Existing customer found: " + existingCustomer.getCustomerName());
            System.out.println("Current tickets purchased: " + existingCustomer.getTicketsPurchased());
            existingCustomer.setTicketsPurchased(existingCustomer.getTicketsPurchased() + ticketsToPurchase);
            customerRepo.save(existingCustomer);
            System.out.println("Updated tickets purchased: " + existingCustomer.getTicketsPurchased());
            return true;
        } else {

            System.out.println("No existing customer found. Creating a new record.");
            Customer newCustomer = new Customer();
            newCustomer.setCustomerName(customerName);
            newCustomer.setEventName(eventName);
            newCustomer.setTicketsPurchased(ticketsToPurchase);
            customerRepo.save(newCustomer);
            return false;
        }
    }



    public List<TicketStatusResponse> getTicketStatusForAllEvents() {
        List<TicketStatusResponse> ticketStatuses = new ArrayList<>();

        List<Vendor> vendors = vendorRepo.findAll();

        for (Vendor vendor : vendors) {
            String eventName = vendor.getEventName();
            int totalTickets = ticketRepository.countByEventName(eventName);
            int soldTickets = ticketRepository.countByEventNameAndSoldTrue(eventName);

            int availableTickets = totalTickets - soldTickets;

            TicketStatusResponse statusResponse = new TicketStatusResponse(eventName, availableTickets, soldTickets);
            ticketStatuses.add(statusResponse);
        }

        return ticketStatuses;
    }


}