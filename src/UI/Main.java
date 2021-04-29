package UI;

import IO.Load;
import IO.Save;
import line.Line;
import station.Station;
import station.StationManager;
import ticket.Passenger;
import ticket.Ticket;
import ticket.TicketManager;
import line.LineManager;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static Scanner input;
    public static Properties properties;
    private static HashSet<Passenger> passengers;
    private static boolean initialized;

    public static void main(String[] args) {
        if(!initialized) {
            properties = Load.loadProperties();
            initializeProgram();
            initialized = true;
        }



        boolean exit = false;
        while (!exit) {
            switch (standardUIMessage.printMenu(standardUIMessage.MENU_SELECT_MODE)) {
                case 1 -> //Vásárlás
                        userUI.start(passengers);
                case 2 -> //Karbantarás
                        maintenanceUI.start();

                case 0 -> //Kilépés
                        exit = true;
            }
        }

        input.close();


    }

    /**
     * Inicializálja az összes inicializálandó osztályt.
     */
    public static void initializeProgram() {
        StationManager.initialize();
        TicketManager.initialize();
        LineManager.initialize();
        input = new Scanner(System.in);
        passengers = new HashSet<>();

        Load.loadAll();

        TicketManager.setManualSeatFee(Integer.parseInt(properties.getProperty("MANUAL_SEAT_FEE", "0")));
    }

    /**
     * Kér a felhasználótól egy {@code int} értéket, addig próbálkozik, ameddig nem jár sikerrel.
     * @return a felhasználótól kapott {@code int}.
     */
    public static int getInt() {
        while (true){
            try {
                return Integer.parseInt(input.next());
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Egész számot kell megadni!");
            }
        }
    }
    public static void setPassengers(HashSet<Passenger> passengers) {
        if (passengers != null) Main.passengers = passengers;
    }
    public static HashSet<Passenger> getPassengers() {
        return passengers;
    }

}
