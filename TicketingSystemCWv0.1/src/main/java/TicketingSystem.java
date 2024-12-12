import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TicketingSystem {
    private final static Logger logger = LogManager.getLogger(TicketingSystem.class);
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the maximum ticket capacity: ");
        int maximumTicketCapacity = scanner.nextInt();

        System.out.print("Enter the total tickets per event: ");
        int totalTickets = scanner.nextInt();

        System.out.print("Enter the ticket release rate per event: ");
        int ticketReleaseRate = scanner.nextInt();

        System.out.print("Enter the customer retrieval rate: ");
        int customerRetrievalRate = scanner.nextInt();

        System.out.print("Enter the number of customers: ");
        int customerCount = scanner.nextInt();

        System.out.print("Enter the number of vendors: ");
        int vendorCount = scanner.nextInt();

        Configuration configuration = new Configuration(totalTickets, ticketReleaseRate, customerRetrievalRate, maximumTicketCapacity, vendorCount, customerCount);

        if (!configuration.validateInputs(vendorCount, ticketReleaseRate, maximumTicketCapacity)) {
            System.err.println("Error: Total tickets exceed the maximum ticket capacity. Adjust inputs.");
            return;
        }

        try{
            TicketPool ticketPool = new TicketPool();

            // Vendor Thread
            List<Thread> vendorThreads = new ArrayList<>();
            for (int i = 1; i <= vendorCount; i++) {
                String eventName = "Event-" + i;
                Vendor vendor = new Vendor(ticketPool, configuration, eventName);
                Thread thread = new Thread(vendor, "Vendor-" + i);
                vendorThreads.add(thread);
                thread.start();

            }

            // Customer Threads
            List<Thread> customerThreads = new ArrayList<>();
            for (int i = 1; i <= customerCount; i++) {
                String eventName = "Event-" + ((i - 1) % vendorCount + 1);
                Customer customer = new Customer(ticketPool, eventName, configuration );
                Thread thread = new Thread(customer, "Customer-" + i);
                customerThreads.add(thread);
                thread.start();
            }

            for (Thread thread : vendorThreads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            for (Thread thread : customerThreads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("\nFinal Ticket Lists:");
            for (String eventName : ticketPool.getEventNames()) {
                List<String> remainingTickets = ticketPool.getTickets(eventName);
                System.out.println(eventName + ": " + remainingTickets);
                FileLogger.saveRemainingTickets(eventName, remainingTickets);
            }

        }catch (Exception e) {
            logger.error("An error occurred while processing.");
        }
        logger.info("Process Completed Successfully.");


    }
}
