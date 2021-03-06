package line.vehicle;

import java.io.Serializable;

/**
 * Eltárolja egy vonat kocsiját, amelyben a székek helyezkednek el.
 */
public class Wagon implements Serializable {
    /** Kocsiban lévő székek */
    private final Seat[] seats;
    /** Kocsi száma */
    private final int wagonNumber;

    /**
     * Létrehoz és inicializál egy új kocsi osztályt.
     * @param seatCount A kocsiba lévő székek darabszáma.
     */
    public Wagon(int seatCount, int wagonNumber){
        seats = new Seat[seatCount];

        //Tömb inicializálása
        for (int i = 0; i < seatCount; i++) {
            seats[i] = new Seat(wagonNumber + "-" + (i+1));
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
     * @return a kocsiba lévő szabad székek számát
     */
    public int getFreeSeatCount(){
        int sum = 0;
        for (Seat s : seats){
            if(s.getStatus() == Seat.FREE) sum++;
        }
        return sum;
    }

    /**
     * @param connectRequest legalább ennyi szék legyen egymás mellett szabadon
     * @return a kocsiba lévő összefüggő szabad székek számát.
     */
    public int getConnectedFreeSeatCount(int connectRequest){
        int sum = 0;
        int connectCounter = 0;

        for (Seat seat : seats) {
            if (seat.getStatus() == Seat.FREE) {
                connectCounter++;
                if (connectCounter >= connectRequest) {
                    sum++;
                }
            } else {
                connectCounter = 0;
            }
        }
        return sum;
    }

    /**
     * Megadja a megadott szék foglaltsági állapotát
     * @param seatNumber ülés indexe
     * @return A szék foglaltsági állapota
     */
    public boolean getSeatStatus(int seatNumber){
        return seats[seatNumber].getStatus();
    }

    /**
     * Lefoglalja a megadott széket.
     * @param seatNumber a lefoglalandó szék indexe
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

    /**
     * @param seatI a kért szék indexe
     * @return a megadott indexű szék osztályát
     */
    public Seat getSeat(int seatI) {
        return seats[seatI];
    }
    public int getWagonNumber() {
        return wagonNumber;
    }

    /**
     * Megkeres egy széket az azonosítója alapján.
     * @param name szék azonosítója
     * @return megtalált szék osztálya
     */
    public Seat searchSeat(String name){
        for (Seat s : seats){
            if(s.getSeatNumber().equalsIgnoreCase(name))
                return s;
        }
        return null;
    }

    @Override
    public String toString() {
        return wagonNumber + ". kocsi, " + getFreeSeatCount() + " szabad ülés.";
    }
}
