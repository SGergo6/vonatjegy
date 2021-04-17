package UI;

import IO.Save;
import IO.input;
import line.Line;
import line.LineManager;
import station.Station;
import station.StationManager;

import java.util.ArrayList;

public abstract class maintenanceUI {
    private static final int EXIT = 0;
    private static final int SELECT_STATION = 1;
    private static final int NEW_LINE = 2;
    private static final int NEW_TRAIN = 3;
    private static final int NEW_STATION = 4;
    private static final int LIST = 5;
    private static final int MANAGE_DELAY = 6;

    /** Karbantartó mód opciói */
    public static final String[] MENU_MAINTENANCE_MAIN = new String[]{
            "Kilépés",
            "Jelenlegi állomás beállítása",
            "Új vonal hozzáadása",
            "Vonat hozzáadása",
            "Állomás hozzáadása",
            "Listázás",
            "Késés kezelése"
    };

    public static void start(){

        boolean exit = false;

        while (!exit) {
            int option = standardUIMessage.printMenu(maintenanceUI.MENU_MAINTENANCE_MAIN);

            switch (option) {

                case SELECT_STATION:
                    break;

                case NEW_LINE:
                    newLine();
                    break;

                case NEW_TRAIN:
                    break;

                case NEW_STATION:
                    Station newStation = newStation();
                    if(newStation != null) {
                        if (StationManager.newStation(newStation)) {
                            System.out.println(newStation + " sikeresen hozzáadva.");
                            Save.saveStations(StationManager.getStations());
                        } else {
                            System.out.println(newStation + " hozzáadása nem sikerült: már létezik állomás ilyen néven.");
                        }
                    }
                    break;

                case LIST:
                    switch (standardUIMessage.printMenu(listUI.MENU_LIST_ALL)) {
                        case listUI.LINES:
                            break;
                        case listUI.STATIONS:
                            listUI.listStations(StationManager.getStations(), false);
                            standardUIMessage.ok();
                            break;
                        case listUI.TRAINS:
                            break;
                    }

                case MANAGE_DELAY:
                    break;

                case EXIT:
                    exit = true;
                    break;
            }
        }
    }



    public static Station newStation(){
        System.out.print("Állomás neve: ");
        String stationName = input.nextLine();
        if(stationName.equals("") || stationName.equals("0")) return null;
        return new Station(stationName);
    }

    public static Line newLine(){
        System.out.println("Vonal neve: ");
        String name = input.nextLine();
        if(name.equals("") || name.equals("0")) return null;

        System.out.println("Megállónkénti jegyár:");
        int price = input.nextInt();
        if(price < 0) return null;

        ArrayList<Station> orderedStations = listUI.listStations(StationManager.getStations(), true);
        ArrayList<Station> selectedStations = new ArrayList<>();
        System.out.println("Állomások sorszáma, érkezési sorrendben, vesszővel elválasztva:");
        String[] selectedStr = input.nextLine().split(",");

        for (int i = 0; i < selectedStr.length; i++) {
            int select;
            try {
                select = Integer.parseInt(selectedStr[i]);
                selectedStations.add(orderedStations.get(select));
            } catch (IndexOutOfBoundsException e){
                System.out.println("Az egyik szám (" + selectedStr[i]
                        + ") érvénytelen! (0 és" + (orderedStations.size()-1) + "között kell lennie)");
                return null;
            } catch (NumberFormatException e){
                if(i != selectedStr.length-1){
                    System.out.println("Az egyik szám nem értelmezhető! " + selectedStr[i]);
                    return null;
                }
            }

            if(selectedStations.size() < 2){
                System.out.println("Legalább 2 állomást érintenie kell a vonalnak!");
                return null;
            }
        }

        return new Line((Station[]) selectedStations.toArray(), name, price);


        //route, name, price
    }
}
