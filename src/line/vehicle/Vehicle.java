package line.vehicle;

import line.LineManager;
import line.Timetable;
import time.Time;

import java.io.Serializable;

/**
 * Ennek az osztálynak a segítségével lehet
 * további járműveket hozzáadni a programhoz.
 */
public abstract class Vehicle implements Serializable {
    /** Megadja, hogy a vonal útvonalát fordítva kell-e értelmezni.
     * Ez a vonat irányát határozza meg, hogy A -> B-be tart, vagy
     * B -> A-ba tart.<br>
     * Az állomás időket mindig az eredeti sorrend szerint kell megadni,
     * ennek a változónak a vonat útvonalának kiírásában van szerepe.*/
    private boolean lineReversed;
    /** Érkezési idő az útvonal összes megállójába */
    protected Time[] stationArrivals;
    private final transient int ID;
    /** Jármű késése, percben */
    private int delay;

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

    /**
     * @return a jármű indulásának idejét az 1. állomásról
     */
    public Time getDepartureTime(){
        //A stationArrivals a vonal útvonal tömbjének sorrendjében tárolja el az időket!
        //A vonal sorrendjében halad, az induló állomás a 0. állomás
        if(!isLineReversed()) {
            return stationArrivals[0];
        } else { //Visszafelé halad, az induló állomás az utolsó állomás
            return stationArrivals[stationArrivals.length-1];
        }
    }

    /**
     * @return a jármű érkezési idejét a végállomásra
     */
    public Time getArrivalTime(){
        //A vonal sorrendjében halad, az érkezési állomás az utolsó állomás
        if(!isLineReversed()) {
            return stationArrivals[stationArrivals.length-1];
        } else { //Visszafelé halad, az érkezési állomás az 1. állomás
            return stationArrivals[0];
        }
    }

    /**
     * Megkeresi és visszaadja egy megadott állomásra történő érkezés idejét.
     * @param stationIndex az állomás sorszáma a {@code line route} tömbjében
     * @return az állomásra való érkezési időt
     */
    public Time getStationArrival(int stationIndex){
        return stationArrivals[stationIndex];
    }

    public int getDelay() {
        return delay;
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }
}
