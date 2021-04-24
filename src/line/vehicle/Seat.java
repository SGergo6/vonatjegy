package line.vehicle;

import java.io.Serializable;

public class Seat implements Serializable {
    private boolean reserved;

    public static final boolean RESERVED = true;
    public static final boolean FREE = false;

    Seat(){
        reserved = false;
    }

    /**
     * Lefoglalja a széket.
     * @return  {@code false}, ha a szék már foglalt volt,
     * {@code true}, ha sikeres a foglalás
     */
    public boolean reserve(){
        if(reserved){
            return false;
        }
        reserved = RESERVED;
        return true;
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
