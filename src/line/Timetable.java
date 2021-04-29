package line;

import station.Station;
import time.Time;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/** Menetrendet tároló osztály. */
public class Timetable {
    /** A jármű a vonal {@code route} tömb útvonalán halad */
    public static final boolean DIRECTION_NORMAL = false;
    /** A jármű <b>fordítva</b> halad a vonal tömb útvonalán */
    public static final boolean DIRECTION_REVERSED = true;

    private final Line line;
    /** Érkezési idő az állomásokra, indexelése megegyezik a {@code Line route} tömbjével. */
    private Time[] arrival;

    /** Létrehoz egy új, üres menetrendet. */
    public Timetable(Line line){
        this.line = line;
        arrival = new Time[line.getRoute().length];
    }

    /**
     * Létrehoz egy új menetrendet, a megadott paraméterekkel feltöltve.
     * @param line vonal
     * @param arrival érkezési idő az állomásokra
     */
    public Timetable(Line line, Time[] arrival){
        this.line = line;
        this.arrival = arrival;
    }

    /**
     * Hozzáad egy állomáshoz egy érkezési időt.
     * @param station az állomás, amihez az időt adja
     * @param time a hozzáadandó időpont
     * @return {@code true}, ha sikerült hozzáadni<br>
     * {@code false}, ha nem sikerült hozzáadni
     */
    public boolean addTime(Station station, Time time){
        Station[] stations = line.getRoute();

        for (int i = 0; i < stations.length; i++) {
            if(stations[i].equals(station)){
                arrival[i] = time;
                return true;
            }
        }

        return false;
    }

    /**
     * Megkeresi az állomáshoz tartozó érkezési időt.
     * @param station a keresendő állomás
     * @return az érkezési időt, vagy ha nincs megadva,
     * vagy az állomás nincs az útvonalon, {@code null}
     */
    public Time getStationArrival(Station station){
        Station[] stations = line.getRoute();

        for (int i = 0; i < stations.length; i++) {
            if(stations[i].equals(station)){
                return arrival[i];
            }
        }

        return null;
    }

    public void setArrival(Time[] arrival) {
        this.arrival = arrival;
    }
    /** @return a menetrendben lévő állomások */
    public Station[] getStations(){
        return line.getRoute();
    }

    public Line getLine() {
        return line;
    }
    public Time[] getArrivals() {
        return arrival;
    }

    /**
     * Kikeres egy állomást a menetrendből a neve alapján.
     * Nem nagybetű érzékeny. Nem kell teljes egyezés.
     * @param name Keresendő állomás
     * @return a megtalált állomás<br>
     * {@code null}, ha az állomás nem volt megtalálható a vonalon
     */
    public Station searchStation(String name){
        for(Station s : line.getRoute()){
            if(s.getName().toLowerCase().contains(name)){
                return s;
            }
        }
        return null;
    }

    /** @return az menetrend méretével (hány megállót érint) */
    public int Size(){
        return arrival.length;
    }

    @Override
    public String toString() {
        StringBuilder tableString = new StringBuilder();
        Station[] route = this.getStations();
        if(arrival[0].greaterThan(arrival[arrival.length-1]))
            Collections.reverse(Arrays.asList(route));

        for (Station station : route){
            tableString.append(station + ": " + this.getStationArrival(station) + "\n");
        }

        return tableString.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timetable timetable = (Timetable) o;
        return line.equals(timetable.line) && Arrays.equals(arrival, timetable.arrival);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(line);
        result = 31 * result + Arrays.hashCode(arrival);
        return result;
    }
}
