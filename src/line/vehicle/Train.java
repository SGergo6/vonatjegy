package line.vehicle;

import java.io.Serializable;

public class Train extends Vehicle implements Serializable {
    private Wagon[] wagons;

    public Train(int wagonCount){
        super();
        wagons = new Wagon[wagonCount];
    }


    public Wagon[] getWagons() {
        return wagons;
    }
    /**
     * Lefoglalja a megadott széket a megadott kocsiban.
     * @param wagonNumber a kocsi száma
     * @param seatNumber a lefoglalandó szék
     * @return {@code false}, ha a szék már foglalt volt a
     * lefoglalás előtt, {@code true}, ha sikers volt a foglalás.
     */
    public boolean reserveSeat(int wagonNumber, int seatNumber) {
        return wagons[wagonNumber].reserveSeat(seatNumber);
    }
    /**
     * Felszabadítja a megadott széket a megadott kocsiban.
     * @param wagonNumber a kocsi száma
     * @param seatNumber a felszabadítandó szék.
     */
    public void freeSeat(int wagonNumber, int seatNumber){
        wagons[wagonNumber].freeSeat(seatNumber);
    }


    /**
     * Beállítja 1 kocsi méretét a megadott méretűre.
     * @param index a beállítandó kocsi sorszáma
     * @param seatCount a kocsiban lévő ülések száma.
     */
    public void setWagon(int index, int seatCount){
        wagons[index] = new Wagon(seatCount, index+1);
    }

    /**
     * Beállítja a vonat összes kocsijának méretét a megadott méretűre.
     * @param seatCount ülések száma 1 kocsiban
     */
    public void setAllWagon(int seatCount){
        for (int i = 0; i < wagons.length; i++) {
            this.setWagon(i, seatCount);
        }
    }

    /**
     * @param wagonI Kocsi sorszáma
     * @param seatI Ülés száma
     * @return Az ülés a megadott kocsiból
     */
    public Seat getSeat(int wagonI, int seatI){
        return wagons[wagonI].getSeat(seatI);
    }

    @Override
    public int getTotalFreeSeatCount() {
        int sum = 0;
        for (Wagon w : wagons){
            sum += w.getFreeSeatCount();
        }
        return sum;
    }

    @Override
    public int getTotalSeatCount() {
        int sum = 0;
        for (Wagon w : wagons){
            sum += w.getSeatCount();
        }
        return sum;
    }

    @Override
    public int getTotalConnectedFreeSeatCount(int connectRequest) {
        int sum = 0;
        for (Wagon w : wagons){
            sum += w.getConnectedFreeSeatCount(connectRequest);
        }
        return sum;
    }
}
