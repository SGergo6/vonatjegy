package line;

import station.Station;
import station.StationManager;
import time.Time;

import java.util.Arrays;
import java.util.Objects;

/**
 * Eltárol egy menetrendet.
 */
public class Timetable {
    /** A jármű a vonal {@code route} tömb útvonalán halad */
    public static final boolean DIRECTION_NORMAL = false;
    /** A jármű <b>fordítva</b> halad a vonal tömb útvonalán */
    public static final boolean DIRECTION_REVERSED = false;

    private Line line;
    private Time[] arrival;

    public Timetable(Line line){
        this.line = line;
        arrival = new Time[line.getRoute().length];
    }

    /**
     * Hozzáad egy állomáshoz egy érkezési időt.
     * @param station az állomás, amihez az időt adja
     * @param time a hozzáadandó időpont
     * @return {@code true}, ha sikerült hozzáadni, {@code false}, ha nem
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
    public Station[] getStations(){
        return line.getRoute();
    }

    public Line getLine() {
        return line;
    }
    public Time[] getArrivals() {
        return arrival;
    }

    public int Size(){
        return arrival.length;
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
