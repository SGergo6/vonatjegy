package line.vehicle;

import line.LineManager;
import line.Timetable;
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

    protected Vehicle(){
        ID = LineManager.nextID();
    }

    /**
     * Beállítja a vonat menetrendjét.
     * @param direction a vonat iránya.<br>Megadása: {@code Timetable.DIRECTION_NORMAL/REVERSED}
     * @param timetable Érkezési idő az állomásokra.<br>Az állomás időket mindig
     *                        a vonal eredeti sorrendje szerint kell megadni!
     */
    public void setTimetable(boolean direction, Timetable timetable){
        lineReversed = direction;
        setStationArrivals(timetable);
    }

    public int getID() {
        return ID;
    }

    /**
     * Visszaadja a vonat menetrendjét.
     * @return a vonat menetrendje
     */
    public Time[] getStationArrivals() {
        return stationArrivals;
    }

    /**
     * @return {@code false}: a vonat a vonal irányában halad.<br>
     * {@code true}: a vonat a vonal útvonalán fordítvam visszafelé halad.
     */
    public boolean isLineReversed() {
        return lineReversed;
    }

    /**
     * Átállítja a vonat haladási irányát.
     * @param lineReversed Timetable.DIRECTION_NORMAL/REVERSED.
     */
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

    /**
     * Beállítja a menetrendet.<br>
     * Ha a vonat a vonal másik irányába halad, azt a {@code lineReversed} változóban
     * kell jelezni.
     * @param timetable a beállítandó menetrend.
     */
    public void setStationArrivals(Timetable timetable) {
        this.stationArrivals = timetable.getArrivals();
    }

}
