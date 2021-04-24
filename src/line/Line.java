package line;

import line.vehicle.Train;
import line.vehicle.Vehicle;
import station.Station;
import time.Time;

import java.io.Serializable;
import java.util.*;

public class Line implements Serializable{
    private final Station[] route;
    private final String name;
    private int price;
    private HashSet<Vehicle> trains;


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
        trains = new HashSet<>();
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
        trains.add(train);
    }

    /**
     * Hozzáad egy új járművet a vonalhoz.
     * @param vehicle valamilyen jármű osztály leszármazott.
     */
    public void newVehicle(Vehicle vehicle){
        trains.add(vehicle);
    }

    /**
     * Megkeres egy vonatot az indulási ideje alapján
     * @param departure indulási idő a vonat 1. állomásáról
     * @return a megtalált vonat osztálya, vagy {@code null}
     */
    public Vehicle findVehicle(Time departure) {
        for(Vehicle vehicle : trains){
            if(vehicle.getDepartureTime().equals(departure)){
                return vehicle;
            }
        }
        return null;
    }

    /**
     * Megkeres egy vonatot egy időtartományban az indulási ideje alapján
     * @param departure indulási idő a vonat 1. állomásáról
     * @param interval időtartomány (+/-), percben megadva
     * @return a megtalált vonatok osztályai tömbbe
     */
    public Vehicle[] findVehicles(Time departure, int interval) {
        ArrayList<Vehicle> found = new ArrayList<>();
        for(Vehicle vehicle : trains){
            if(vehicle.getDepartureTime().interval(departure.subMins(interval), departure.addMins(interval))){
                found.add(vehicle);
            }
        }

        return (Vehicle[]) found.toArray();
    }


    /**
     * Visszaad egy menetrend osztályt, amit ebből a vonalból és a megadott vonatból állít össze.
     * @param vehicle A vonat, aminek a menetrendje kell
     * @return menetrend osztály, állomásokkal és időkkel összerendelve.
     */
    public Timetable getTimetable(Vehicle vehicle){
        if(vehicle.getStationArrivals().length != route.length){
            throw new IllegalArgumentException("Ez a vonat nem ezen a vonalon közlekedik!");
        }
        Timetable t = new Timetable(this);
        t.setArrival(vehicle.getStationArrivals());
        return t;
    }

    public Station[] getRoute() {
        return route;
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public HashSet<Vehicle> getTrains() {
        return new HashSet<>(Collections.unmodifiableCollection(trains));
    }
    public Station getDeparture(){
        return route[0];
    }
    public Station getArrival(){
        return route[route.length-1];
    }
    public void updatePrice(int price) {
        this.price = price;
    }

    /**
     * Visszaadja egy állomás sorszámát az útvonalból.
     * @param station a keresendő állomás
     * @return az állomás sorszáma, {@code null}, ha az állomás
     * nem szerepel az útvonalon.
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
     * @return {@code true} ha megegyezik a 2 vonal <b>neve</b>
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
        return Objects.hash(name);
    }
}

