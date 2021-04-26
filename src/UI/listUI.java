package UI;

import line.Line;
import line.Timetable;
import line.comparator.VehicleDepartureComparator;
import line.vehicle.Vehicle;
import station.Station;

import java.util.*;

/** Listázással kapcsolatos inteface metódusok */
public abstract class listUI {

    public static final int EXIT = 0;
    public static final int LINES = 1;
    public static final int STATIONS = 2;
    public static final int TRAINS = 3;
    public static final int ROUTE = 4;
    public static final int TIMETABLE = 5;

    /** Az összes listázható opció */
    public static final String[] MENU_LIST_ALL = new String[]{
            "Vissza",
            "Vonalak",
            "Állomások",
            "Vonatok (egy vonalon)",
            "Vonal útvonala",
            "Vonat menetrendje"
    };



    /**
     * Kilistázza az összes megadott állomást, az állomások neve szerint rendezve.
     * @param stationsCollection kilistázandó állomások
     * @param showIndex ha {@code true}, mutatja az állomás neve előtt annak indexét is.<br>
     * <b>Az indexet +1-el mutatja</b>, hogy legyen lehetőség a vissza implementálására is!
     * @param sorted ha {@code true}, akkor sorba rendezi az állomásokat név szerint
     * @return a név szerint rendezett állomások listáját
     */
    public static ArrayList<Station> listStations(Collection<Station> stationsCollection, boolean showIndex, boolean sorted){
        ArrayList<Station> stations = new ArrayList<>(stationsCollection);
        if(sorted)
            Collections.sort(stations);

        for (int i = 0; i < stations.size(); i++) {
            if(showIndex)
                System.out.print(i+1 + ". ");
            System.out.println(stations.get(i));
        }
        return stations;
    }


    /**
     * Kilistázza az összes megadott vonalat, a megadott comparátorral.
     * @param lineCollection kilistázandó vonalak
     * @param showIndex ha {@code true}, mutatja a vonal neve előtt annak indexét is.<br>
     * <b>Az indexet +1-el mutatja</b>, hogy legyen lehetőség a vissza implementálására is!
     * @return a rendezett vonalak listáját
     */
    public static ArrayList<Line> listLines(Collection<Line> lineCollection, Comparator<Line> comparator, boolean showIndex){
        ArrayList<Line> lines = new ArrayList<>(lineCollection);
        lines.sort(comparator);

        for (int i = 0; i < lines.size(); i++) {
            if(showIndex)
                System.out.print(i+1 + ". ");
            System.out.println(lines.get(i));
        }
        return lines;
    }

    public static void listTimetable(Timetable t){
        for (Station station : t.getStations()){

        }
    }

    /**
     * Kilistázza a megadott vonal összes járművének az indulási idejét
     * és állomását, indulási idő szerint sorba rendezve.
     */
    public static void listVehicles(Line line){
        Station start_normal = line.getRoute()[0];
        Station start_reverse = line.getRoute()[line.getRoute().length-1];

        ArrayList<Vehicle> vehicles = new ArrayList<>(line.getVehicles());
        vehicles.sort(new VehicleDepartureComparator());

        for (Vehicle vehicle : line.getVehicles()){
            System.out.println(vehicle.isLineReversed() == Timetable.DIRECTION_NORMAL ? start_normal : start_reverse
            + ": " + vehicle.getDepartureTime());
        }
    }


}
