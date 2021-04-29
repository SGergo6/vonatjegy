package IO;

import UI.Main;
import line.Line;
import line.LineManager;
import station.Station;
import station.StationManager;
import ticket.Passenger;
import ticket.Ticket;
import ticket.TicketManager;

import java.io.*;
import java.util.*;

public abstract class Save {

    public static final String STATIONS_FILE = "stations";
    public static final String LINES_FILE = "lines";
    public static final String PASSENGERS_FILE = "passengers";
    public static final String TICKETS_FILE = "tickets";

    /**
     * Kimenti az összes elmentendő adatot a programból.<br>
     * Mentési sorrend:
     * <ol>
     *     <li>Stations</li>
     *     <li>Lines</li>
     *     <li>Passengers</li>
     *     <li>Tickets</li>
     * </ol>
     */
    public static void save(){
        try {
            FileOutputStream f = new FileOutputStream("vonatjegy.save");
            ObjectOutputStream out = new ObjectOutputStream(f);

            out.writeObject(StationManager.getStations());
            out.writeObject(LineManager.getLines());
            out.writeObject(Main.getPassengers());
            out.writeObject(TicketManager.getTickets());
        } catch (IOException e){
            System.out.println("A mentés nem sikerült! Oka: " + e.getMessage());
        }
    }

    public static void saveProperties(Properties p){
        try {
            FileOutputStream f = new FileOutputStream("global.txt");
            p.store(f, "Kézi ülésválasztás ára");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
