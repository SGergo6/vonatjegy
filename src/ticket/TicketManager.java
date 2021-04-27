package ticket;

import line.Line;
import line.vehicle.Seat;

import java.util.*;

/**
 * Programszintű jegy menedzselő osztály.<br>
 * Ez az osztály tárolja el az összes megvásárolt jegyet egy {@code HashSet}
 * típusú tárolóban, duplikáció így nem léphet fel.<br><br>
 * <b>Az osztályt használat előtt inicializálni kell.</b>
 */
public abstract class TicketManager {
    private static HashSet<Ticket> tickets;
    private static int manualSeatFee;

    /**
     * Inicializálja az osztályt.
     */
    public static void initialize(){
        tickets = new HashSet<>();
        manualSeatFee = 0;
    }

    /**
     * Megvárásol egy jegyet, lefoglalja a széket és levonja a pénzt az egyenlegből.<br>
     * Egy utas egy vonatra csak 1x vásárolhat jegyet.
     * @param ticket a megvásárolni kívánt jegy
     * @return {@code false}, ha ez az utas ugyanerre a vonatra és helyre már vett jegyet,
     * vagy a szék már foglalt volt.<br>{@code true}, ha sikeres volt.
     */
    public static boolean purchase(Ticket ticket) {
        if(ticket.getSeat().getStatus() == Seat.RESERVED){
            return false;
        }
        if(!tickets.add(ticket)){
            return false;
        }
        if(!ticket.getSeat().reserve()) return false;
        ticket.getPassenger().purchase(ticket);
        return true;
    }

    /**
     * Törli a listából és visszafizeti az utasnak a jegy árát.
     * @param ticket a törölni kívánt jegy
     * @return {@code false}, ha az utas nem rendelkezik a jeggyel vagy a jegy nincs is a listában már.<br>
     * {@code true}, ha sikeres.
     */
    public static boolean refund(Ticket ticket) {
        if(!tickets.remove(ticket)){
            return false;
        }
        ticket.getSeat().free();
        ticket.getPassenger().refund(ticket);
        return true;
    }

    /**
     * Megkeres egy utashoz tartozó összes jegyet.
     * @param passenger a lekérdezett utas
     * @return az utas összes birtokolt jegye
     */
    public static ArrayList<Ticket> findTickets(Passenger passenger){
        ArrayList<Ticket> returnTickets = new ArrayList<>(getTickets());
        returnTickets.removeIf(t -> !t.getPassenger().equals(passenger));
        return returnTickets;
    }

    /**
     * Megkeres egy utashoz tartozó összes jegyet egy vonalon.
     * @param passenger a lekérdezett utas
     * @return az utas összes birtokolt jegye
     */
    public static ArrayList<Ticket> findTickets(Passenger passenger, Line line){
        ArrayList<Ticket> passTickets = findTickets(passenger);
        passTickets.removeIf(t -> !t.getLine().equals(line));
        return new ArrayList<>(passTickets);
    }


    /**
     * @return az összes jegyet tartalmazó {@code HashSet}-et.
     */
    public static HashSet<Ticket> getTickets() {
        return new HashSet<>(tickets);
    }
    /**
     * @return a kézzel kiválasztott ülés felára
     */
    public static int getManualSeatFee() {
        return manualSeatFee;
    }
    /**
     * Beállítja a kézi helyválasztás árát.
     * @param manualSeatFee új ár
     */
    public static void setManualSeatFee(int manualSeatFee) {
        TicketManager.manualSeatFee = manualSeatFee;
    }

    public static void setTickets(HashSet<Ticket> tickets) {
        TicketManager.tickets = tickets;
    }
}
