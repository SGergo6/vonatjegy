package UI;

import line.Line;
import line.Timetable;
import line.comparator.LineNameComparator;
import line.comparator.VehicleDepartureComparator;
import line.vehicle.Vehicle;
import station.Station;
import ticket.Ticket;

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

    /**
     * Kilistázza a megadott vonal összes járművének az indulási idejét
     * és állomását, indulási idő szerint sorba rendezve.
     * @param showIndex ha {@code true}, mutatja a vonal neve előtt annak indexét is.<br>
     * <b>Az indexet +1-el mutatja</b>, hogy legyen lehetőség a vissza implementálására is!
     * @return a sorba rendezett vonatok listáját.
     */
    public static ArrayList<Vehicle> listVehicles(Line line, boolean showIndex){
        Station start_normal = line.getRoute()[0];
        Station start_reverse = line.getRoute()[line.getRoute().length-1];

        ArrayList<Vehicle> vehicles = new ArrayList<>(line.getVehicles());
        vehicles.sort(new VehicleDepartureComparator());

        int i = 0;
        for (Vehicle vehicle : vehicles){
            if(showIndex) System.out.print(i+1 + ". ");
            System.out.println((vehicle.isLineReversed() == Timetable.DIRECTION_NORMAL ? start_normal : start_reverse)
            + ":\t\t" + vehicle.getDepartureTime() + "\tkésés: " + vehicle.getDelay() + "p");
            i++;
        }

        return vehicles;
    }

    /**
     * Kilistázza az összes megadott jegyet, ár szerint rendezve.
     * @param tickets kiírandó jegyek
     * @param showIndex ha {@code true}, mutatja a vonal neve előtt annak indexét is.<br>
     * <b>Az indexet +1-el mutatja</b>, hogy legyen lehetőség a vissza implementálására is!
     * @return az ár szerint sorba rendezett jegyeket
     */
    public static ArrayList<Ticket> listTickets(Collection<Ticket> tickets, boolean showIndex){
        ArrayList<Ticket> sortedTickets = new ArrayList<>(tickets);
        Collections.sort(sortedTickets);

        for (int i = 0; i < sortedTickets.size(); i++) {
            if(showIndex) System.out.println(i + 1 + ". ");
            System.out.println(sortedTickets.get(i));
        }
        return sortedTickets;
    }


    /**
     * Kér a felhasználótól egy vonalat a megadott vonalak közül.
     * @param lines választható vonalak
     * @return választott vonal
     */
    public static Line selectLine(Collection<Line> lines){
        if(lines.size() == 0){
            System.out.println("Nincs 1 kiírható vonal se.");
            standardUIMessage.ok();
            return null;
        }
        ArrayList<Line> listedLines = listLines(lines, new LineNameComparator(), true);
        System.out.print("Vonal sorszáma: ");
        int selectedLine = Main.getInt()-1;
        if(selectedLine < 0 || selectedLine >= listedLines.size()) return null;
        return listedLines.get(selectedLine);
    }

    /**
     * Kiválaszt a megadott vonal összes járműje közül 1-et.
     * @param line A megadott vonal, ahol a járműt keresi
     * @return a kiválasztott jármű, vagy {@code null}, ha nem sikerült kiválasztani
     */
    public static Vehicle selectVehicle(Line line){
        if(line.getVehicles().size() == 0){
            System.out.println("Nincs 1 vonat se ezen a vonalon.");
            standardUIMessage.ok();
            return null;
        }
        ArrayList<Vehicle> vehicles = listVehicles(line, true);
        System.out.print("Vonat sorszáma: ");
        int selectedTrain = Main.getInt()-1;
        if(selectedTrain < 0 || selectedTrain >= vehicles.size()) return null;
        return vehicles.get(selectedTrain);
    }

    public static Station selectStation(Collection<Station> stations){
        if(stations.size() == 0){
            System.out.println("Nincs 1 kiválasztható állomás se.");
            standardUIMessage.ok();
            return null;
        }
        ArrayList<Station> orderedStations = listStations(stations, true, true);
        System.out.print("Vonal sorszáma: ");
        int selectedStation = Main.getInt()-1;
        if(selectedStation < 0 || selectedStation >= orderedStations.size()) return null;
        return orderedStations.get(selectedStation);
    }

    public static Ticket selectTicket(Collection<Ticket> tickets){
        if(tickets.size() == 0){
            System.out.println("Nincs 1 kiválasztható jegy se.");
            standardUIMessage.ok();
            return null;
        }
        ArrayList<Ticket> orderedTickets = listTickets(tickets, true);
        System.out.print("Jegy sorszáma: ");
        int selectedTicket = Main.getInt()-1;
        if(selectedTicket < 0 || selectedTicket >= orderedTickets.size()) return null;
        return orderedTickets.get(selectedTicket);
    }

}
