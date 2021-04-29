package IO;

import UI.Main;
import line.LineManager;
import station.StationManager;
import ticket.TicketManager;

import java.io.*;
import java.util.*;

/** Adatok mentését megvalósító osztály. */
public abstract class Save {

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

    /**
     * Kimenti a program globális beállításait.
     */
    public static void saveProperties(Properties p){
        try {
            FileOutputStream f = new FileOutputStream("global.txt");
            p.store(f, "Kézi ülésválasztás ára");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
