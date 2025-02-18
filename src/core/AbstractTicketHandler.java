package core;

public abstract class AbstractTicketHandler {
    protected TicketPool ticketPool;

    public AbstractTicketHandler(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    public abstract void handleTickets();
}
