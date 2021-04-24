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
     * @param ticket megvásárolandó jegy
     */
    void purchase(Ticket ticket){
        balance -= ticket.getPrice();
        tickets.add(ticket);
    }

    /**
     * Visszaadja egy utas számára a jegy árát, és eltávolítja a jegyet a listájából.
     * @param ticket az eltávolítandó jegy
     * @return {@code true}, ha sikeres, {@code false}, ha az utas nem rendelkezik a jeggyel.
     */
    boolean refund(Ticket ticket) {
        if(tickets.remove(ticket)) {
            balance += ticket.getPrice();
            return true;
        } else {
            return false;
        }
    }

    public HashSet<Ticket> getTickets() {
        return new HashSet<>(Collections.unmodifiableCollection(tickets));
    }
}
