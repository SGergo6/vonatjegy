package line.vehicle;

import line.LineManager;
import time.Time;

/**
 * Ennek az osztálynak a segítségével lehet
 * további járműveket hozzáadni a programhoz.
 */
public abstract class Vehicle {
    /** Megadja, hogy a vonal útvonalát fordítva kell-e értelmezni.
     * Ez a vonat irányát határozza meg, hogy A -> B-be tart, vagy
     * B -> A-ba tart.<br>
     * Az állomás időket mindig az eredeti sorrend szerint kell megadni,
     * ennek a változónak a vonat útvonalának kiírásában van szerepe.*/
    private boolean lineReversed;
    /** Érkezési idő az útvonal összes megállójába */
    protected Time[] stationArrivals;
    private final int ID;

    /** A vonat a vonal tömb útvonalán halad */
    public static final boolean DIRECTION_NORMAL = false;
    /** A vonat <b>fordítva</b> halad a vonal tömb útvonalán */
    public static final boolean DIRECTION_REVERSED = false;

    protected Vehicle(){
        ID = LineManager.nextID();
    }

    /**
     * Beállítja a vonat menetrendjét.
     * @param direction a vonat iránya.<br>Megadása: {@code Vehicle.DIRECTION_NORMAL/REVERSED}
     * @param stationArrivals Érkezési idő az állomásokra.<br>Az állomás időket mindig
     *                        a vonal eredeti sorrendje szerint kell megadni!
     */
    public void setTimetable(boolean direction, Time[] stationArrivals){
        lineReversed = direction;
        setStationArrivals(stationArrivals);
    }

    public int getID() {
        return ID;
    }
    public Time[] getStationArrivals() {
        return stationArrivals;
    }
    public boolean isLineReversed() {
        return lineReversed;
    }
    public void setLineReversed(boolean lineReversed) {
        this.lineReversed = lineReversed;
    }

    /**
     * Beállítja az állomásokra való érkezési időket a paraméterben megadottakra.<br>
     * Az állomás időket mindig a vonal eredeti sorrendje szerint kell megadni!
     * Ha a vonat a vonal másik irányába halad, azt a {@code lineReversed} változóban
     * kell jelezni.
     * @param stationArrivals érkezési idő az állomásokra.
     */
    public void setStationArrivals(Time[] stationArrivals) {
        this.stationArrivals = stationArrivals;
    }

}
