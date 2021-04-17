import IO.input;
import IO.Load;
import UI.maintenanceUI;
import UI.userUI;
import station.Station;
import station.StationManager;
import ticket.Passenger;
import ticket.TicketManager;
import line.LineManager;
import UI.standardUIMessage;

import java.io.FileNotFoundException;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        initializeProgram();

        HashSet<Passenger> passengers = new HashSet<>();

        boolean exit = false;
        while (!exit) {
            switch (standardUIMessage.printMenu(standardUIMessage.MENU_SELECT_MODE)) {
                case 1 -> //Vásárlás
                        userUI.start();
                case 2 -> //Karbantarás
                        maintenanceUI.start();

                case 0 -> //Kilépés
                        exit = true;
            }
        }


    }

    /**
     * Inicializálja az összes inicializálandó osztályt.
     */
    public static void initializeProgram() {
        input.initialize(System.in);
        StationManager.initialize();
        TicketManager.initialize();
        LineManager.initialize();

        StationManager.setStations(Load.loadStations());
    }
}
