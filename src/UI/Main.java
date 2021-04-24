package UI;

import IO.Load;
import UI.maintenanceUI;
import UI.userUI;
import station.StationManager;
import ticket.Passenger;
import ticket.TicketManager;
import line.LineManager;
import UI.standardUIMessage;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Scanner input;

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

        input.close();
        IO.input.close();


    }

    /**
     * Inicializálja az összes inicializálandó osztályt.
     */
    public static void initializeProgram() {
        IO.input.initialize(System.in);
        StationManager.initialize();
        TicketManager.initialize();
        LineManager.initialize();
        input = new Scanner(System.in);

        StationManager.setStations(Load.loadStations());
        LineManager.setLines(Load.loadLines());
    }

    /**
     * Kér a felhasználótól egy {@code int} értéket, addig próbálkozik, ameddig nem jár sikerrel.
     * @param errorMsg ezt a megadott üzenetet írja ki hiba esetén a felhasználónak, mielőtt újrakéri az értéket.
     * @return a felhasználótól kapott {@code int}.
     */
    public static int getInt(String errorMsg) {
        while (true){
            try {
                return Integer.parseInt(input.next());
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println(errorMsg);
            }
        }
    }


}
