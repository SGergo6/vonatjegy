package line;

import line.vehicle.Seat;

public class SeatOccupiedException extends RuntimeException {
    Seat seat;

    public SeatOccupiedException(String message, Seat seat){
        super(message);
        this.seat = seat;
    }

    public SeatOccupiedException(Seat seat){
        super("A megadott szék már foglalt volt!");
        this.seat = seat;
    }

    private SeatOccupiedException(){}

    public Seat getSeat() {
        return seat;
    }
}
