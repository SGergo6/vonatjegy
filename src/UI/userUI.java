package UI;

import IO.Save;
import line.Line;
import line.LineManager;
import line.vehicle.Vehicle;
import ticket.Passenger;
import ticket.Ticket;
import ticket.TicketManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public abstract class userUI {

    private static final int EXIT = 0;
    private static final int PURCHASE_TICKET = 1;
    private static final int LIST_TICKET = 2;
    private static final int REFUND_TICKET = 3;
    private static final int SEARCH_TRAIN = 4;

    /** Vásárlás mód opciói */
    public static final String[] MENU_BUY_MAIN = new String[]{
            "Kilépés",
            "Jegy vásárlása",
            "Jegyek listázása",
            "Jegy visszatérítése",
            "Vonatok keresése"
    };

    public static void start(HashSet<Passenger> passengers) {
        boolean exit = false;
        Passenger loggedInUser = login(passengers);
        if(loggedInUser == null) return;


        while (!exit) {
            int option = standardUIMessage.printMenu(MENU_BUY_MAIN);
            switch (option) {

                case PURCHASE_TICKET:
                    break;

                case LIST_TICKET:
                    listUI.listTickets(TicketManager.findTickets(loggedInUser), false);
                    standardUIMessage.ok();
                    break;

                case REFUND_TICKET:
                    Ticket selectTicket = listUI.selectTicket(TicketManager.findTickets(loggedInUser));
                    if(selectTicket == null) break;
                    System.out.println("Biztosan szeretnéd ezt a jegyet visszatéríteni?\n" + selectTicket);
                    if(!standardUIMessage.yesNo()) break;
                    TicketManager.refund(selectTicket);
                    System.out.println("A kiválasztott jegy sikeresen visszafizetve.");
                    standardUIMessage.ok();
                    break;

                case SEARCH_TRAIN:
                    Line selectedLine = listUI.selectLine(LineManager.getLines());
                    if(selectedLine == null) break;
                    ArrayList<Vehicle> sortVehicles = listUI.listVehicles(selectedLine, true);

                    System.out.print("Vonat sorszáma a menetrendhez: ");
                    int selected = -1;
                    try{
                        selected = Integer.parseInt(Main.input.next())-1;
                    } catch (NumberFormatException ignored) {}
                    if(selected < 0 || selected > sortVehicles.size()) break;
                    System.out.println(selectedLine.getTimetable(sortVehicles.get(selected)));
                    standardUIMessage.ok();
                    break;

                case EXIT: //Kilépés
                    exit = true;
                    break;
            }
        }

    }


    /**
     * Bejelentkeztet egy felhasználót
     * @param passengers az összes felhasználó
     * @return a belépett felhasználó
     */
    private static Passenger login(HashSet<Passenger> passengers){
        System.out.print("Felhasználónév: ");
        String name = Main.input.next();
        if (name.equals("") || name.equals("0")) return null;
        Iterator<Passenger> it = passengers.iterator();
        while(it.hasNext()){
            Passenger passenger = it.next();
            if(passenger.getName().equalsIgnoreCase(name)){
                return passenger;
            }
        }

        System.out.println("Szeretnéd létrehozni \"" + name + "\" felhasználót?");
        if(standardUIMessage.yesNo()){
            Passenger registered = new Passenger(name);
            passengers.add(registered);
            Save.save(Save.PASSENGERS_FILE, passengers);
            return registered;
        }
        return null;
    }

}
