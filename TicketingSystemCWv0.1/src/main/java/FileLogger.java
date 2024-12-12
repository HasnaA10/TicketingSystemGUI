import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileLogger {

    public static void logToFile(String message, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


    public static void saveCustomerDetails(String eventName, List<String> purchasedTickets) {
        String message = "Event: " + eventName + ", Tickets: " + purchasedTickets;
        logToFile(message, "Customers.txt");
    }

    public static void saveVendorDetails(String eventName, List<String> ticketsAdded) {
        String message = "Event: " + eventName + ", Tickets Added: " + ticketsAdded;
        logToFile(message, "Vendors.txt");
    }

    public static void saveRemainingTickets(String eventName, List<String> remainingTickets) {
        String message = "Event: " + eventName + ", Remaining Tickets: " + remainingTickets;
        logToFile(message, "RemainingTickets.txt");
    }
}
