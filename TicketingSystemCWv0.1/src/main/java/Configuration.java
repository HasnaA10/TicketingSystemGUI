
public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maximumTicketCapacity;
    private int vendorCount;
    private int customerCount;

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maximumTicketCapacity, int vendorCount, int customerCount) {
        if (totalTickets <= 0 || ticketReleaseRate <= 0 || customerRetrievalRate <= 0 || maximumTicketCapacity <= 0 || vendorCount <= 0 || customerCount <= 0) {
            throw new IllegalArgumentException("All configuration values must be positive integers.");
        }
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.vendorCount = vendorCount;
        this.customerCount = customerCount;
    }

    public Configuration(int ticketReleaseRate, int customerRetrievalRate){
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
    }
    public Configuration(){}

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaximumTicketCapacity() {
        return maximumTicketCapacity;
    }

    public int getVendorCount() {
        return vendorCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public static boolean validateInputs(int vendorCount, int ticketReleaseRate, int maximumTicketCapacity) {
        int totalTicketsFromVendors = vendorCount * ticketReleaseRate;
        return totalTicketsFromVendors <= maximumTicketCapacity;
    }
}
