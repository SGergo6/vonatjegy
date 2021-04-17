package UI;

import station.Station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/** Listázással kapcsolatos inteface metódusok */
public abstract class listUI {

    public static final int LINES = 0;
    public static final int STATIONS = 1;
    public static final int TRAINS = 2;

    /** Az összes listázható opció */
    public static final String[] MENU_LIST_ALL = new String[]{
            "Vonalak",
            "Állomások",
            "Vonatok (egy vonalon)"
    };



    /**
     * Kilistázza az összes megadott állomást, az állomások neve szerint rendezve.
     * @param stationsHash kilistázandó állomások
     * @param showIndex ha {@code true}, mutatja az állomás neve előtt annak indexét is
     * @return a név szerint rendezett állomások listáját
     */
    public static ArrayList<Station> listStations(HashSet<Station> stationsHash, boolean showIndex){
        ArrayList<Station> stations = new ArrayList<>(stationsHash);
        Collections.sort(stations);

        for (int i = 0; i < stations.size(); i++) {
            if(showIndex)
                System.out.print(i + ". ");
            System.out.println(stations.get(i));
        }
        return stations;
    }

    /**
     * Kilistázza az összes megadott állomást, rendezés nélkül.
     * @param stations kilistázandó állomások
     */
    public static void listStations(ArrayList<Station> stations){
        for (int i = 0; i < stations.size(); i++) {
            System.out.println(stations.get(i));
        }
    }

    /**
     * Kilistázza az összes állomást, és bekér a felhasználótól 1 állomást.
     * @param stationHash listázandó állomások
     * @return a kiválasztott állomást
     */
    public static Station getStation(HashSet<Station> stationHash){
        ArrayList<Station> stations = listStations(stationHash, true);

        System.out.print("\nVálasztott állomás sorszáma: ");
        return stations.get(standardUIMessage.szam_be(0, stations.size()));
    }
}
