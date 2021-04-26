package IO;

import UI.standardUIMessage;
import line.Line;
import station.Station;
import ticket.Passenger;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;

public abstract class Load {

    /**
     * Ha a betöltés nem sikerül (IOException),
     * akkor a betöltést meg kell szakítani.<br>
     * Betöltési hibát nem lehet kezelni,
     * egy hibás fájl esetén a többi osztály se
     * tud felépülni rendesen.
     * @param throwable A hiba forrása
     */
    private static void throwLoadFailed(Throwable throwable, String filename) throws LoadFailed{
        System.out.println("Hiba történt a beolvasás során: " + throwable.getMessage());
        System.out.println("A " + filename + " fájl beolvasása nem sikerült.");
        System.out.println("A program betöltése nem folytatható. Ha a hiba újraindítást követően " +
                "is fennáll, akkor törölni kell a mentést, és újra kell konfigurálni a programot.");
        standardUIMessage.ok();
        throw new LoadFailed(throwable);
    }

    public static HashSet<Station> loadStations(){
        try {
            FileInputStream f = new FileInputStream(Save.STATIONS_FILE);
            ObjectInputStream in = new ObjectInputStream(f);
            HashSet<Station> stations = new HashSet<>();

            try {
                while (true) {
                    Station s = (Station) in.readObject();
                    if (s != null) {
                        stations.add(s);
                    } else {
                        break;
                    }
                }
            } catch (EOFException ignored) {}

            return stations;
        } catch (FileNotFoundException e) {
            return new HashSet<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throwLoadFailed(e, Save.STATIONS_FILE);
            return null;
        }
    }

    public static HashSet<Line> loadLines(){
        try {
            FileInputStream f = new FileInputStream(Save.LINES_FILE);
            ObjectInputStream in = new ObjectInputStream(f);
            HashSet<Line> lines = new HashSet<>();

            try {
                while (true) {
                    Line l = (Line) in.readObject();
                    if (l != null) {
                        lines.add(l);
                    } else {
                        break;
                    }
                }
            } catch (EOFException ignored) {}

            return lines;
        } catch (FileNotFoundException e) {
            return new HashSet<>();
        } catch (IOException | ClassNotFoundException e) {
            throwLoadFailed(e, Save.LINES_FILE);
            return null;
        }
    }

    public static HashSet<Passenger> loadPassengers(){
        try{
            FileInputStream f = new FileInputStream(Save.PASSENGERS_FILE);
            ObjectInputStream in = new ObjectInputStream(f);
            HashSet<Passenger> passengers = new HashSet<>();

            try {
                while (true) {
                    Passenger p = (Passenger) in.readObject();
                    if (p != null) {
                        passengers.add(p);
                    } else {
                        break;
                    }
                }
            } catch (EOFException ignored) {}

            return passengers;
        } catch (FileNotFoundException e) {
            return new HashSet<>();
        } catch (IOException | ClassNotFoundException e) {
            throwLoadFailed(e, Save.PASSENGERS_FILE);
            return null;
        }
    }

    public static Properties loadProperties(){
        Properties properties = new Properties();

        try {
            FileInputStream f = new FileInputStream("global.txt");
            properties.load(f);
        } catch (IOException e) {
            properties.setProperty("MANUAL_SEAT_FEE", "0");
        }
        return properties;
    }
}
