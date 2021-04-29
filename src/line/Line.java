package line;

import line.vehicle.Train;
import line.vehicle.Vehicle;
import station.Station;
import time.Time;

import java.io.Serializable;
import java.util.*;

/** Egy vonal információit és vonatait tárolja el. */
public class Line implements Serializable{
    /** Vonal útvonala sorrendben */
    private final Station[] route;
    /** Vonal neve */
    private final String name;
    /** Vonal megállónkénti ára */
    private int price;
    /** Vonalon közlekedő járművek */
    private final HashSet<Vehicle> vehicles;


    /**
     * Létrehoz egy új vonalat.
     * @param route A vonal útvonala
     * @param name A vonal neve (egyedinek kell lennie)
     * @param price A vonal megállónkénti jegyára
     */
    public Line(Station[] route, String name, int price) {
        this.route = route;
        this.name = name;
        this.price = price;
        vehicles = new HashSet<>();
    }

    /**
     * Hozzáad egy új vonatot a vonalhoz.
     * @param wagonCount kocsik száma a vonton
     * @param seatPerWagon ülések száma 1 kocsiban
     * @param direction a vonat menetiránya {@code Timetable.DIRECTION_NORMAL/REVERSED}
     * @param timetable érkezési idő állomásokra
     */
    public void newTrain(int wagonCount, int seatPerWagon, boolean direction, Timetable timetable){
        Train train = new Train(wagonCount);
        train.setAllWagon(seatPerWagon);
        train.setTimetable(direction, timetable);
        vehicles.add(train);
    }

    /**
     * Hozzáad egy új járművet a vonalhoz.
     * @param vehicle valamilyen jármű osztály leszármazott.
     */
    public boolean newVehicle(Vehicle vehicle){
        return vehicles.add(vehicle);
    }

    /**
     * Megkeres egy járművet az indulási ideje alapján
     * @param departure indulási idő a jármű 1. állomásáról
     * @return a megtalált jármű osztálya, vagy {@code null}
     */
    public Vehicle findVehicle(Time departure) {
        for(Vehicle vehicle : vehicles){
            if(vehicle.getDepartureTime().equals(departure)){
                return vehicle;
            }
        }
        return null;
    }

    /**
     * Megkeres egy járművet egy időtartományban az indulási ideje alapján
     * @param departure indulási idő a jármű 1. állomásáról
     * @param interval időtartomány (+/-), percben megadva
     * @return a megtalált vonatok osztályai tömbbe
     */
    public Vehicle[] findVehicles(Time departure, int interval) {
        ArrayList<Vehicle> found = new ArrayList<>();
        for(Vehicle vehicle : vehicles){
            if(vehicle.getDepartureTime().interval(departure.subMins(interval), departure.addMins(interval))){
                found.add(vehicle);
            }
        }

        return found.toArray(new Vehicle[0]);
    }


    /**
     * Visszaad egy menetrend osztályt, amit ebből a vonalból és a megadott vonatból állít össze.
     * @param vehicle A vonat, aminek a menetrendje kell
     * @return menetrend osztály, állomásokkal és időkkel összerendelve.
     */
    public Timetable getTimetable(Vehicle vehicle){
        if(vehicle.getStationArrivals().length != route.length){
            return null;
        }
        return new Timetable(this, vehicle.getStationArrivals());
    }

    /** @return az útvonal másolatát */
    public Station[] getRoute() {
        return route.clone();
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    /** @return a vonalon lévő vonatok másolatát */
    public HashSet<Vehicle> getVehicles() {
        return new HashSet<>(vehicles);
    }
    /** Indulási állomás */
    public Station getDeparture(){
        return route[0];
    }
    /** Érkezési állomás */
    public Station getArrival(){
        return route[route.length-1];
    }
    /** Átírja a megállónkénti árat az új értékre. */
    public void updatePrice(int price) {
        this.price = price;
    }

    /**
     * Visszaadja egy állomás sorszámát az útvonalból.
     * @param station a keresendő állomás
     * @return az állomás sorszáma,<br>
     * {@code null}, ha az állomás nem szerepel az útvonalon.
     */
    public Integer getRouteStationIndex(Station station) {
        for (int i = 0; i < route.length; i++) {
            if(route[i].getName().equals(station.getName())){
                return i;
            }
        }

        return null;
    }

    /**
     * Megvizsgálja, hogy 2 vonal ugyanaz-e.<br>
     * 1 néven csak 1 vonal lehetséges, nem lehet 1 néven 2 különböző útvonalú vonal is.<br>
     * A vonal állomásainak irányát a jármű osztály határozza meg a {@code lineReversed} változóban.
     * (Honnan hova tart)
     * @param o Összehasonlítandó objektum
     * @return {@code true} ha megegyezik a 2 vonal neve
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return name.equalsIgnoreCase(line.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }

    @Override
    public String toString() {
        return name + ", " + price + "Ft/megálló, " + vehicles.size() + "db vonat";
    }
}

