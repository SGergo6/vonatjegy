package ticket;

import line.Line;
import line.vehicle.Seat;
import line.SeatOccupiedException;

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
     * @throws SeatOccupiedException a jegyen szereplő szék már foglalt volt
     * @throws IllegalArgumentException ez az utas ugyanerre a vonatra és helyre már vett jegyet
     */
    public static void purchase(Ticket ticket) throws SeatOccupiedException, IllegalArgumentException{
        if(ticket.getSeat().getStatus() == Seat.RESERVED){
            throw new SeatOccupiedException(ticket.getSeat());
        }
        if(!tickets.add(ticket)){
            throw new IllegalArgumentException("A jegy már szerepel a listában!");
        }
        ticket.getSeat().reserve();
        ticket.getPassenger().purchase(ticket);
    }

    /**
     * Törli a listából és visszafizeti az utasnak a jegy árát.
     * @param ticket a törölni kívánt jegy
     * @throws TicketNotFoundException az utas nem rendelkezik a jeggyel vagy a jegy nincs is a listában már.
     */
    public static void refund(Ticket ticket) throws TicketNotFoundException{
        if(!tickets.remove(ticket)){
            throw new TicketNotFoundException("A jegy nem található a listában!", ticket);
        }
        ticket.getSeat().free();
        ticket.getPassenger().refund(ticket);
    }

    /**
     * Megkeres egy utashoz tartozó összes jegyet.
     * @param passenger a lekérdezett utas
     * @return az utas összes birtokolt jegye
     */
    public static ArrayList<Ticket> findTickets(Passenger passenger){
        return new ArrayList<>(Collections.unmodifiableCollection(passenger.getTickets()));
    }

    /**
     * Megkeres egy utashoz tartozó összes jegyet egy vonalon.
     * @param passenger a lekérdezett utas
     * @return az utas összes birtokolt jegye
     */
    public static ArrayList<Ticket> findTickets(Passenger passenger, Line line){
        HashSet<Ticket> passTickets = passenger.getTickets();
        passTickets.removeIf(t -> !t.getLine().equals(line));
        return new ArrayList<>(Collections.unmodifiableCollection(passTickets));
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
}
