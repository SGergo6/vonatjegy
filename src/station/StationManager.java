package station;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Programszintű állomás menedzselő osztály.<br>
 * Ez az osztály tárolja el az állomásokat egy {@code HashSet} típusú tárolóban,
 * duplikáció így nem léphet fel.<br><br>
 * <b>Az osztályt használat előtt inicializálni kell.</b>
 */
public abstract class StationManager {
    private static int nextStationID;
    private static HashSet<Station> stations;

    /**
     * Inicializálja az osztályt.
     */
    public static void initialize(){
        if(stations == null) {
            nextStationID = 0;
            stations = new HashSet<>();
        }
    }

    /**
     * Megadja a következő egyedi állomás azonosító számot.
     * @return az állomás egyedi azonosító száma.
     */
    static int nextStationID(){
        int returnID = nextStationID;
        nextStationID++;
        return returnID;
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

    /**
     * @return a teljes állomás tároló {@code HashSet}-et.
     */
    public static HashSet<Station> getStations() {
        return new HashSet<>(stations);
    }

    /**
     * Megkeres egy állomást név alapján. Nem nagybetű érzékeny.
     * @param stationName a keresendő állomás neve
     * @return A megtalált állomás osztályát
     * @throws StationNotFoundException ha az állomás nem létezik
     */
    public static Station searchStation(String stationName) throws StationNotFoundException{
        Iterator<Station> i = stations.iterator();
        Station search = new Station(stationName);

        while(i.hasNext()){
            Station iteratedStation = i.next();
            if(iteratedStation.equals(search)){
                return iteratedStation;
            }
        }
        throw new StationNotFoundException(stationName);
    }

    public static void setStations(HashSet<Station> stations) {
        StationManager.stations = stations;
    }
}
