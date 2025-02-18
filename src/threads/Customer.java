package threads;

import core.AbstractTicketHandler;
import core.TicketPool;
import logging.Logger;

public class Customer extends AbstractTicketHandler implements Runnable {
    public Customer(TicketPool ticketPool) {
        super(ticketPool);
    }

    @Override
    public void run() {
        while (true) {
            String ticket = ticketPool.removeTicket();
            if (ticket != null) {
                Logger.log("Customer retrieved: " + ticket);
            } else {
                Logger.log("Customer found no tickets available.");
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Logger.log("Customer interrupted.");
                break;
            }
        }
    }

    @Override
    public void handleTickets() {
        run();
    }
}
