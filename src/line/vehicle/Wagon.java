package line.vehicle;

import line.SeatOccupiedException;
import line.vehicle.Seat;

/**
 * Eltárolja egy jármű kocsiját, amelyben a székek helyezkednek el.
 */
public class Wagon {
    private final Seat[] seats;

    /**
     * Létrehoz és inicializál egy új kocsi osztályt.
     * @param seatCount A kocsiba lévő székek darabszáma.
     */
    public Wagon(int seatCount){
        seats = new Seat[seatCount];

        //Tömb inicializálása
        for (int i = 0; i < seatCount; i++) {
            seats[i] = new Seat();
        }
    }

    /**
     * @return a kocsiba lévő székek számát
     */
    public int getSeatCount(){
        return seats.length;
    }

    /**
     * Megadja a kocsi összes székének foglaltsági állapotát
     * @return A szék foglaltsági állapota
     */
    public boolean[] getSeatStatus(){
        boolean[] status = new boolean[getSeatCount()];
        for (int i = 0; i < seats.length; i++) {
            status[i] = seats[i].getStatus();
        }
        return status;
    }

    /**
     * Lefoglalja a megadott széket.
     * @param seatNumber a lefoglalandó szék
     * @throws SeatOccupiedException ha a szék már foglalt volt a lefoglalás előtt
     */
    public void reserveSeat(int seatNumber) throws SeatOccupiedException{
        seats[seatNumber].reserve();
    }

    /**
     * Felszabadítja a megadott széket.
     * @param seatNumber a felszabadítandó szék.
     */
    public void freeSeat(int seatNumber){
        seats[seatNumber].free();
    }




}
