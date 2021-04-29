package IO;

import UI.Main;
import UI.standardUIMessage;
import line.Line;
import line.LineManager;
import station.Station;
import station.StationManager;
import ticket.Passenger;
import ticket.Ticket;
import ticket.TicketManager;

import java.io.*;
import java.util.HashSet;
import java.util.Properties;

/** Adatok betöltését végző osztály. */
public abstract class Load {

    /**
     * Ha a betöltés nem sikerül (IOException),
     * akkor a betöltést meg kell szakítani.<br>
     * Betöltési hibát nem lehet kezelni,
     * egy hibás fájl esetén a többi osztály se
     * tud felépülni rendesen.
     * @param throwable A hiba forrása
     */
    private static void throwLoadFailed(Throwable throwable) throws LoadFailed{
        System.out.println("Hiba történt a beolvasás során: " + throwable.getMessage());
        System.out.println("A vonatjegy.save fájl beolvasása nem sikerült.");
        System.out.println("A program betöltése nem folytatható. Ha a hiba újraindítást követően " +
                "is fennáll, akkor törölni kell a mentést, és újra kell konfigurálni a programot.");
        standardUIMessage.ok();
        throw new LoadFailed(throwable);
    }

    /**
     * Betölti az összes adatot a <i>vonatjegy.save</i> fájlból a megfelelő managerekbe.
     */
    @SuppressWarnings("unchecked")
    public static void loadAll(){
        try {
            FileInputStream f = new FileInputStream("vonatjegy.save");
            ObjectInputStream in = new ObjectInputStream(f);

            StationManager.setStations((HashSet<Station>) in.readObject());
            LineManager.setLines((HashSet<Line>) in.readObject());
            Main.setPassengers((HashSet<Passenger>) in.readObject());
            TicketManager.setTickets((HashSet<Ticket>) in.readObject());
        } catch (FileNotFoundException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throwLoadFailed(e);
        }
    }

    /**
     * Betölti a globális beállításokat.
     */
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
