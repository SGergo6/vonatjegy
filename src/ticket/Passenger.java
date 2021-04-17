package ticket;

import java.util.Collections;
import java.util.HashSet;

public class Passenger extends Person{
    private HashSet<Ticket> tickets;

    /**
     * Létrehoz egy új utast.
     * @param name az utas neve
     */
    public Passenger(String name){
        this.name = name;
        tickets = new HashSet<>();
    }

    /**
     * Hozzáad egy jegyet az utas jegyeihez, és levonja az egyenlegéből.
     * @param ticket
     */
    void purchase(Ticket ticket){
        balance -= ticket.getPrice();
        //
        tickets.add(ticket);
    }

    /**
     * Visszaadja egy utas számára a jegy árát, és eltávolítja a jegyet a listájából.
     * @param ticket az eltávolítandó jegy
     * @throws TicketNotFoundException az utas nem rendelkezik a jeggyel.
     */
    void refund(Ticket ticket) throws TicketNotFoundException{
        if(tickets.remove(ticket)) {
            balance += ticket.getPrice();
        } else {
            throw new TicketNotFoundException("Az utas nem rendelkezik ezzel a jeggyel.", ticket);
        }
    }

    public HashSet<Ticket> getTickets() {
        return new HashSet<>(Collections.unmodifiableCollection(tickets));
    }
}
