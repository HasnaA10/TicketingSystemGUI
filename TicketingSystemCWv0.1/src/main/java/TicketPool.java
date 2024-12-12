import java.util.*;

public class TicketPool {

    private final List<String> tickets = Collections.synchronizedList(new LinkedList<>());


    private final Set<String> eventNames = Collections.synchronizedSet(new HashSet<>());

    public void addEvent(String eventName) {
        synchronized (eventNames) {
            eventNames.add(eventName);
        }
    }

    public List<String> getEventNames() {
        synchronized (eventNames) {
            return new ArrayList<>(eventNames);
        }
    }

    public void addTickets(List<String> newTickets) {
        synchronized (tickets) {
            tickets.addAll(newTickets);
            tickets.notifyAll();
        }
    }

    public List<String> buyTickets(String eventName, int maxTickets) {
        synchronized (tickets) {
            List<String> purchasedTickets = new ArrayList<>();
            Iterator<String> iterator = tickets.iterator();

            while (iterator.hasNext() && purchasedTickets.size() < maxTickets) {
                String ticket = iterator.next();
                if (ticket.contains("Event: " + eventName)) {
                    purchasedTickets.add(ticket);
                    iterator.remove();
                }
            }

            if (purchasedTickets.isEmpty()) {
                System.err.println(Thread.currentThread().getName() + " - Tickets unavailable for " + eventName);
            } else if (purchasedTickets.size() < maxTickets) {
                System.out.println(Thread.currentThread().getName() + " - Only " + purchasedTickets.size() + " tickets available for " + eventName);
            }

            return purchasedTickets;
        }
    }

    public List<String> getTickets(String eventName) {
        synchronized (tickets) {
            List<String> eventSpecificTickets = new ArrayList<>();
            for (String ticket : tickets) {
                if (ticket.contains("Event: " + eventName)) {
                    eventSpecificTickets.add(ticket);
                }
            }
            return eventSpecificTickets;
        }
    }


}
