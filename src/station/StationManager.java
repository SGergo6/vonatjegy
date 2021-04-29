package station;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Programszintű állomás menedzselő osztály.<br>
 * Ez az osztály tárolja el az állomásokat egy {@code HashSet} típusú tárolóban,
 * duplikáció így nem léphet fel.<br><br>
 * <b>Az osztályt használat előtt inicializálni kell.</b>
 */
public abstract class StationManager {
    /** Összes létrehozott állomás tárolója*/
    private static HashSet<Station> stations;

    /**
     * Inicializálja az osztályt alapértelmezett értékekkel.
     */
    public static void initialize(){
        stations = new HashSet<>();
    }


    /**
     * Hozzáad egy új állomást a {@code HashSet}-hez.<br>
     * Egy állomás duplikációnak számít akkor,
     * ha megegyezik a neve egy már létező állomással. Nem nagybetű érzékeny.<br>
     * Ha egy állomás már létezik a listában, nem történik semmi.
     * @param stationName az új állomás neve
     * @return {@code true} ha sikerült hozzáadni<br>
     * {@code false}, ha már szerepelt a listában.
     */
    public static boolean newStation(String stationName){
        Station s = new Station(stationName);
        return stations.add(s);
    }

    /**
     * Hozzáad egy új állomást a {@code HashSet}-hez.<br>
     * Egy állomás duplikációnak számít akkor,
     * ha megegyezik a neve egy már létező állomással. Nem nagybetű érzékeny.<br>
     * Ha egy állomás már létezik a listában, nem történik semmi.
     * @param station az hozzáadandó állomás
     * @return {@code true} ha sikerült hozzáadni<br>
     * {@code false}, ha már szerepelt a listában.
     */
    public static boolean newStation(Station station){
        return stations.add(station);
    }

    /** @return az állomásokat tároló {@code HashSet} másolatával. */
    public static HashSet<Station> getStations() {
        return new HashSet<>(stations);
    }

    /**
     * Megkeres egy állomást név alapján. Nem nagybetű érzékeny.
     * @param stationName a keresendő állomás neve
     * @return A megtalált állomás osztályát<br>
     * {@code null}, ha az állomás nem létezik
     */
    public static Station searchStation(String stationName){
        Iterator<Station> i = stations.iterator();
        Station search = new Station(stationName);

        while(i.hasNext()){
            Station iteratedStation = i.next();
            if(iteratedStation.equals(search)){
                return iteratedStation;
            }
        }

        return null;
    }

    /** Beállítja az állomás listát a megadott listára, kivéve, ha az null. */
    public static void setStations(HashSet<Station> stations) {
        if (stations != null) StationManager.stations = stations;

    }
}
