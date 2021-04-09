package ticket;

import line.Line;
import line.vehicle.Vehicle;
import station.Station;
import line.vehicle.Seat;
import line.vehicle.Train;

import java.util.Objects;

public class Ticket {
    private Station from;
    private Station to;
    private Vehicle vehicle;
    private Seat seat;
    private int price;
    private Passenger passenger;

    /**
     * Létrehoz egy jegy osztályt.
     * @param line vonal, amin a jármű halad
     * @param from felszálló állomás
     * @param to leszálló állomás
     * @param vehicle vonat, amire a jegy érvényes
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

        this.price = Math.abs(line.getRouteStationIndex(to) - line.getRouteStationIndex(from)) * line.getPrice();

        if(manualSeat) {
            price += TicketManager.getManualSeatFee();
        }

    }

    /**
     * Megegyezik, ha az utas, az ülés és a vonat megegyezik.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ticket ticket = (Ticket) obj;
        return vehicle.equals(ticket.vehicle) && seat.equals(ticket.seat) && passenger.equals(ticket.passenger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, seat, passenger);
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
     * @return vonat, amire a jegy érvényes
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
}