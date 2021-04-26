package line.vehicle;

import java.io.Serializable;

/**
 * Eltárolja egy jármű kocsiját, amelyben a székek helyezkednek el.
 */
public class Wagon implements Serializable {
    private final Seat[] seats;
    private final int wagonNumber;

    /**
     * Létrehoz és inicializál egy új kocsi osztályt.
     * @param seatCount A kocsiba lévő székek darabszáma.
     */
    public Wagon(int seatCount, int wagonNumber){
        seats = new Seat[seatCount];

        //Tömb inicializálása
        for (int i = 0; i < seatCount; i++) {
            seats[i] = new Seat(wagonNumber + "-" + i+1);
        }

        this.wagonNumber = wagonNumber;
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
     * @return  {@code false}, ha a szék már foglalt volt a lefoglalás előtt,
     * {@code true}, ha sikeres a foglalás
     */
    public boolean reserveSeat(int seatNumber){
        return seats[seatNumber].reserve();
    }

    /**
     * Felszabadítja a megadott széket.
     * @param seatNumber a felszabadítandó szék.
     */
    public void freeSeat(int seatNumber){
        seats[seatNumber].free();
    }

    public Seat getSeat(int seatI) {
        return seats[seatI];
    }
}
