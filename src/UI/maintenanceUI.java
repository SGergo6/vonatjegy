package UI;

import IO.Save;
import line.Line;
import line.LineManager;
import line.comparator.LineNameComparator;
import line.comparator.LineTrainCountComparator;
import line.comparator.PriceComparator;
import station.Station;
import station.StationManager;

import java.util.ArrayList;
import java.util.Comparator;

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
                    Line line = newLine();
                    if (line != null){
                        if(!LineManager.addLine(line)){
                            System.out.println("A vonal hozzáadása sikertelen, ilyen nevű vonal már létezik.");
                            standardUIMessage.ok();
                        } else {
                            Save.save(Save.LINES_FILE, LineManager.getLines());
                        }
                    }
                    break;

                case NEW_TRAIN:
                    break;

                case NEW_STATION:
                    Station newStation = newStation();
                    if(newStation != null) {
                        if (StationManager.newStation(newStation)) {
                            System.out.println(newStation + " sikeresen hozzáadva.");
                            Save.save(Save.STATIONS_FILE, StationManager.getStations());
                        } else {
                            System.out.println(newStation + " hozzáadása nem sikerült: már létezik állomás ilyen néven.");
                            standardUIMessage.ok();
                        }
                    }
                    break;

                case LIST:
                    switch (standardUIMessage.printMenu(listUI.MENU_LIST_ALL)) {

                        case listUI.LINES:
                            System.out.println("Mi szerint rendezze a vonalakat?");
                            String[] options = new String[]{
                                    "Vissza", "Név", "Ár", "Vonatok száma"
                            };
                            Comparator<Line> comparator = null;
                            switch (standardUIMessage.printMenu(options)) {
                                case 1 -> //Név
                                        comparator = new LineNameComparator();
                                case 2 -> //Ár
                                        comparator = new PriceComparator();
                                case 3 -> //Vonatok száma
                                        comparator = new LineTrainCountComparator();
                            }

                            if(comparator != null) {
                                listUI.listLines(LineManager.getLines(), comparator, false);
                                standardUIMessage.ok();
                            }
                            break;

                        case listUI.STATIONS:
                            listUI.listStations(StationManager.getStations(), false);
                            standardUIMessage.ok();
                            break;

                        case listUI.TRAINS:
                            break;

                        case listUI.EXIT:
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
        String stationName = Main.input.next();
        if(stationName.equals("") || stationName.equals("0")) return null;
        return new Station(stationName);
    }

    public static Line newLine() {
        //route, name, price
        System.out.print("Vonal neve: ");
        String name = Main.input.next();
        if (name.equals("") || name.equals("0")) return null;

        System.out.print("Megállónkénti jegyár: ");
        int price = Main.getInt("Egész számnak kell lennie!");
        if (price < 0) return null;

        ArrayList<Station> orderedStations = listUI.listStations(StationManager.getStations(), true);
        System.out.println("Állomások sorszáma, érkezési sorrendben, vesszővel elválasztva:");
        String[] selectedStr = Main.input.next().split(",");

        //Ismétlődő megállóindexek keresése
        for (int i = 0; i < selectedStr.length; i++) {
            for (int j = 0; j < selectedStr.length; j++) {
                if(i != j && selectedStr[i].equals(selectedStr[j])) {
                    System.out.println("Egy állomás csak egyszer szerepelhet!");
                    standardUIMessage.ok();
                    return null;
                }
            }
        }

        ArrayList<Station> selectedStations = new ArrayList<>();
        for (int i = 0; i < selectedStr.length; i++) {
            try {
                int selectI = Integer.parseInt(selectedStr[i]);
                selectedStations.add(orderedStations.get(selectI));
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Az egyik szám (" + selectedStr[i]
                        + ") érvénytelen! (0 és" + (orderedStations.size() - 1) + "között kell lennie)");
                standardUIMessage.ok();
                return null;
            } catch (NumberFormatException e) {
                //Ha a végére is vesszőt tett, akkor ne legyen baj
                if (i != selectedStr.length - 1) {
                    System.out.println("Az egyik szám nem értelmezhető! " + selectedStr[i]);
                    standardUIMessage.ok();
                    return null;
                }
            }
        }

        if (selectedStations.size() < 2) {
            System.out.println("Legalább 2 állomást érintenie kell a vonalnak!");
            standardUIMessage.ok();
            return null;
        }

        return new Line( selectedStations.toArray(new Station[0]), name, price);
    }
}
