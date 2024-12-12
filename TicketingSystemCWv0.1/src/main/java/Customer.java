import java.util.*;

public class Customer implements Runnable {
    private final String eventName;
    private final TicketPool ticketPool;
    private final Configuration configuration;


    public Customer(TicketPool ticketPool, String eventName, Configuration configuration) {
        this.ticketPool = ticketPool;
        this.eventName = eventName;
        this.configuration = configuration;
    }

    @Override
    public void run() {
        int maxTickets = configuration.getCustomerRetrievalRate();
        List<String> purchasedTickets = ticketPool.buyTickets(eventName, maxTickets);
        if (!purchasedTickets.isEmpty()) {
            FileLogger.saveCustomerDetails(eventName, purchasedTickets);
            System.out.println(Thread.currentThread().getName() + " purchased: " + purchasedTickets);
        }
    }
}
