package core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TicketPool {
    private final List<String> tickets = Collections.synchronizedList(new LinkedList<>());

    public synchronized void addTickets(String ticket) {
        tickets.add(ticket);
    }

    public synchronized String removeTicket() {
        return tickets.isEmpty() ? null : tickets.remove(0);
    }

    public synchronized List<String> getTickets() {
        return new LinkedList<>(tickets);
    }
}
