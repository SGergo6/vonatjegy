package line.vehicle;

import line.LineManager;
import line.Timetable;
import time.Time;
import time.TimeException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Ennek az osztálynak a segítségével lehet
 * további járműveket hozzáadni a programhoz.
 */
public abstract class Vehicle implements Serializable {
    /** Megadja, hogy a vonal útvonalát fordítva kell-e értelmezni.
     * Ez a jármű irányát határozza meg, hogy A -> B-be tart, vagy
     * B -> A-ba tart.<br>
     * Az állomás időket mindig az eredeti sorrend szerint kell megadni,
     * ennek a változónak a jármű útvonalának kiírásában van szerepe.*/
    private boolean lineReversed;
    /** Érkezési idő az útvonal összes megállójába */
    private Time[] stationArrivals;
    /** Jármű késése, percben */
    private int delay;

    /**
     * Beállítja a jármű menetrendjét.
     * @param direction a jármű iránya.<br>Megadása: {@code Timetable.DIRECTION_NORMAL/REVERSED}
     * @param timetable Érkezési idő az állomásokra.<br>Az állomás időket mindig
     *                        a vonal eredeti sorrendje szerint kell megadni!
     */
    public void setTimetable(boolean direction, Timetable timetable){
        lineReversed = direction;
        setStationArrivals(timetable);
    }

    /**
     * Visszaadja a jármű menetrendjét.
     * @return a jármű menetrendje, a késést is hozzáadva minden állomáshoz.
     * @throws TimeException ha a késést hozzáadva az idő túllépte az ábrázolható 24 órát.
     */
    public Time[] getStationArrivals() throws TimeException{
        if(delay == 0){
            return stationArrivals.clone();
        }

        Time[] delayStationArrivals = stationArrivals.clone();
        for (int i = 0; i < delayStationArrivals.length; i++) {
            delayStationArrivals[i] = delayStationArrivals[i].addMins(this.delay);
        }
        return delayStationArrivals;
    }

    /**
     * @return {@code false}: a jármű a vonal irányában halad.<br>
     * {@code true}: a jármű a vonal útvonalán fordítvam visszafelé halad.
     */
    public boolean isLineReversed() {
        return lineReversed;
    }

    /**
     * Átállítja a jármű haladási irányát.
     * @param lineReversed Timetable.DIRECTION_NORMAL/REVERSED.
     */
    public void setLineReversed(boolean lineReversed) {
        this.lineReversed = lineReversed;
    }

    /**
     * Beállítja az állomásokra való érkezési időket a paraméterben megadottakra.<br>
     * Az állomás időket mindig a vonal eredeti sorrendje szerint kell megadni!
     * Ha a jármű a vonal másik irányába halad, azt a {@code lineReversed} változóban
     * kell jelezni.
     * @param stationArrivals érkezési idő az állomásokra.
     */
    public void setStationArrivals(Time[] stationArrivals) {
        this.stationArrivals = stationArrivals;
    }

    /**
     * Beállítja a menetrendet.<br>
     * Ha a jármű a vonal másik irányába halad, azt a {@code lineReversed} változóban
     * kell jelezni.
     * @param timetable a beállítandó menetrend.
     */
    public void setStationArrivals(Timetable timetable) {
        this.stationArrivals = timetable.getArrivals();
    }

    /**
     * @return a jármű indulásának idejét az 1. állomásról,
     * a késés nincs hozzáadva (talán sikerült elindulni késés nélkül).
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
     * @return a jármű érkezési idejét a végállomásra,
     * a késést is beleszámolva.)
     * @throws TimeException ha a késést hozzáadva túllépi az ábrázolható 24 órát
     */
    public Time getArrivalTime() throws TimeException {
        //A vonal sorrendjében halad, az érkezési állomás az utolsó állomás
        if(!isLineReversed()) {
            return stationArrivals[stationArrivals.length-1].addMins(delay);
        } else { //Visszafelé halad, az érkezési állomás az 1. állomás
            return stationArrivals[0].addMins(delay);
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

    /**
     * Hozzáad megadott percnyi késést.
     * @param delay Hozzáadandó késés (percben)
     * @return {@code true}, ha sikerült hozzáadni<br>
     * {@code false}, ha nem sikerült hozzáadni.<br><br>
     * A hozzáadás sikertelen, ha az új késéssel az egyik
     * megállóba 24 óra után vagy 0 óra előtt érkezik.
     */
    public boolean addDelay(int delay){
        try{
            this.delay += delay;
            this.getStationArrivals();
            return true;
        } catch (TimeException e){
            this.delay -= delay;
            return false;
        }
    }

    /**
     * Megegyezik 2 jármű, ha ugyanabba az irányba mennek, és ugyanakkor érnek ode minden állomásra.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return lineReversed == vehicle.lineReversed && Arrays.equals(stationArrivals, vehicle.stationArrivals);
    }

    /**
     * Irány és állomási érkezési idő alapján generálja
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(lineReversed);
        result = 31 * result + Arrays.hashCode(stationArrivals);
        return result;
    }
}
