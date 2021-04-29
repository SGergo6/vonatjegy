package ticket;

import line.Line;
import line.Timetable;
import line.vehicle.Vehicle;
import station.Station;
import line.vehicle.Seat;

import java.io.Serializable;
import java.util.Objects;

/** Jegy tárolására használható osztály. */
public class Ticket implements Serializable, Comparable<Ticket> {
    private final Line line;
    private final Station from;
    private final Station to;
    private final Vehicle vehicle;
    private final Seat seat;
    private final int price;
    private final Passenger passenger;

    /**
     * Létrehoz egy jegy osztályt.
     * @param line vonal, amin a jármű halad
     * @param from felszálló állomás
     * @param to leszálló állomás
     * @param vehicle jármű, amire a jegy érvényes
     * @param seat lefoglalt szék (helyjegy)
     * @param passenger a vásárló utas
     * @param manualSeat {@code true}, ha a vásárló kézzel választott széket (extra költség merülhet fel).
     */
    public Ticket(Line line, Station from, Station to, Vehicle vehicle, Seat seat, Passenger passenger, boolean manualSeat) {
        if(from.equals(to)){
            throw new IllegalArgumentException("Ugyanaz az induló és érkező állomás");
        }
        this.from = from;
        this.to = to;
        this.vehicle = vehicle;
        this.seat = seat;
        this.passenger = passenger;
        this.line = line;

        int price = Math.abs(line.getRouteStationIndex(to) - line.getRouteStationIndex(from)) * line.getPrice();

        if(manualSeat) {
            price += TicketManager.getManualSeatFee();
        }

        this.price = price;

    }

    /**
     * Megegyezik, ha az utas és a vonat megegyezik.
     * Mivel 1 utas 1 vonaton csak 1x szerepelhet, ezért az ülést nem vizsgálja.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ticket ticket = (Ticket) obj;
        return vehicle.equals(ticket.vehicle) && passenger.equals(ticket.passenger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, passenger);
    }

    /**
     * @return Felszálló állomás
     */
    public Station getFrom() {
        return from;
    }
    /**
     * @return Leszálló állomás
     */
    public Station getTo() {
        return to;
    }
    /**
     * @return jármű, amire a jegy érvényes
     */
    public Vehicle getVehicle() {
        return vehicle;
    }
    /**
     * @return lefoglalt szék (helyjegy)
     */
    public Seat getSeat() {
        return seat;
    }
    /**
     * @return a jegy végső ára
     */
    public int getPrice() {
        return price;
    }
    /**
     * @return a vásárló utas
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * @return a vonal amin a jár a jármű
     */
    public Line getLine() {
        return line;
    }

    /**
     * Kiírja a jegyet a következő formában:<br>
     * vonat neve, induló állomás, idő -> érkezési állomás, idő, ülés száma, jegy ára.<br><br>
     * Példa:<br>
     * S80 Keleti 11:30 -> Siófok 13:30, 1-5 ülés. 230Ft
     */
    @Override
    public String toString() {
        Timetable table = line.getTimetable(vehicle);

        return this.line.getName() + "\t" + from.getName() + " " + table.getStationArrival(from)
                + " -> " + to.getName() + " " + table.getStationArrival(to) + ". " + seat.getSeatNumber() + " ülés.\t"
                + price + "Ft";
    }

    /** Összehasonlít 2 jegyet az áruk alapján. */
    @Override
    public int compareTo(Ticket t) {
        return Integer.compare(this.getPrice(), t.getPrice());
    }

}
