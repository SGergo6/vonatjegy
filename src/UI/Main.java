package UI;

import IO.Load;
import station.StationManager;
import ticket.Passenger;
import ticket.TicketManager;
import line.LineManager;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    /** A program fő scannere */
    public static Scanner input;
    /** A program globális beállításai */
    public static Properties properties;
    /** Program által ismert utasok listája */
    private static HashSet<Passenger> passengers;

    public static void main(String[] args) {
        properties = Load.loadProperties();
        initializeProgram();
        input.useDelimiter("\n");


        boolean exit = false;
        while (!exit) {
            switch (standardUIMessage.selectMenu(standardUIMessage.MENU_SELECT_MODE)) {
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
     * Inicializálja az program összes fontos inicializálandó osztályát,
     * mint a managerek, és ezeket értékekkel is feltölti, ha már van egy használható mentés.
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
     * Kér a felhasználótól egy {@code int} értéket. Addig próbálkozik, ameddig nem jár sikerrel.
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
