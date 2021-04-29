package ticket;

import java.io.Serializable;
import java.util.ArrayList;

/** Utast és annak adatait tároló osztály. */
public class Passenger extends Person implements Serializable {
    /** Utas egyenlege */
    private int balance;

    /**
     * Létrehoz egy új utast.
     * @param name az utas neve
     */
    public Passenger(String name){
        super(name);
        balance = 0;
    }

    /**
     * Levonja az utas egyenlegéből a jegy árát.
     * @param ticket megvásárolandó jegy
     */
    void purchase(Ticket ticket){
        balance -= ticket.getPrice();
    }

    /**
     * Visszaadja egy utas számára a jegy árát.
     * @param ticket az visszafizetendő jegy
     */
    void refund(Ticket ticket) {
        balance += ticket.getPrice();
    }

    /** Megkeresi és visszaadja az összes, utas által birtokolt jegyet. */
    public ArrayList<Ticket> getTickets() {
        return TicketManager.findTickets(this);
    }

    /** @return az utas egyenlegét */
    public int getBalance() {
        return balance;
    }
}
