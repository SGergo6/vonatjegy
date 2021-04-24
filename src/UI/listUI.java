package UI;

import line.Line;
import line.comparator.LineNameComparator;
import station.Station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/** Listázással kapcsolatos inteface metódusok */
public abstract class listUI {

    public static final int EXIT = 0;
    public static final int LINES = 1;
    public static final int STATIONS = 2;
    public static final int TRAINS = 3;

    /** Az összes listázható opció */
    public static final String[] MENU_LIST_ALL = new String[]{
            "Vissza",
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
     * Kilistázza az összes megadott vonalat, a megadott comparátorral.
     * @param lineHash kilistázandó vonalak
     * @param showIndex ha {@code true}, mutatja a vonal neve előtt annak indexét is
     * @return a rendezett vonalak listáját
     */
    public static ArrayList<Line> listLines(HashSet<Line> lineHash, Comparator<Line> comparator, boolean showIndex){
        ArrayList<Line> lines = new ArrayList<>(lineHash);
        lines.sort(comparator);

        for (int i = 0; i < lines.size(); i++) {
            if(showIndex)
                System.out.print(i + ". ");
            System.out.println(lines.get(i));
        }
        return lines;
    }


}
