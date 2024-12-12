import java.util.*;

public class Vendor implements Runnable {
    private final String eventName;
    private final TicketPool ticketPool;


    private final Configuration configuration;
    private final Random random = new Random();

    public Vendor(TicketPool ticketPool, Configuration configuration, String eventName) {
        this.ticketPool = ticketPool;
        this.configuration = configuration;
        this.eventName = eventName;
        ticketPool.addEvent(eventName);
    }

    @Override
    public void run() {
        int ticketsAdded = 0;
        int ticketReleaseRate = configuration.getTicketReleaseRate();

        while (ticketsAdded < ticketReleaseRate) {
            int toAdd = Math.min(random.nextInt(5) + 1, ticketReleaseRate - ticketsAdded);

            List<String> newTickets = new ArrayList<>();
            for (int i = 0; i < toAdd; i++) {
                newTickets.add("TicketID-" + (ticketsAdded + i + 1) + " for Event: " + eventName);
            }

            ticketPool.addTickets(newTickets);
            ticketsAdded += toAdd;

            FileLogger.saveVendorDetails(eventName, newTickets);

            System.out.println(Thread.currentThread().getName() + " added " + toAdd +
                    " tickets to " + eventName + ". Total tickets added: " + ticketsAdded);

            try {
                Thread.sleep(random.nextInt(100) + 50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println(Thread.currentThread().getName() + " completed adding tickets for " + eventName);
    }


}
