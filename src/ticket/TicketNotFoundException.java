package ticket;

public class TicketNotFoundException extends RuntimeException{
    Ticket ticket;

    public TicketNotFoundException(String message, Ticket ticket){
        super(message);
        this.ticket = ticket;
    }

    public TicketNotFoundException(Ticket ticket){
        this.ticket = ticket;
    }

    private TicketNotFoundException(){}

    public Ticket getTicket() {
        return ticket;
    }
}
