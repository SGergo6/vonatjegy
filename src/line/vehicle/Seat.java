package line.vehicle;

import line.SeatOccupiedException;

public class Seat{
    private boolean reserved;

    public static final boolean RESERVED = true;
    public static final boolean FREE = false;

    Seat(){
        reserved = false;
    }

    /**
     * Lefoglalja a széket.
     * @throws SeatOccupiedException ha a szék már foglalt volt
     */
    public void reserve() throws SeatOccupiedException{
        if(reserved){
            throw new SeatOccupiedException(this);
        }
        reserved = RESERVED;
    }

    /**
     * Felszabadítja a széket.
     */
    public void free(){
        reserved = FREE;
    }

    /**
     * @return {@code true}, ha foglalt<br>
     * {@code false}, ha szabad
     */
    public boolean getStatus(){
        return reserved;
    }
}
