package UI;

import IO.Save;
import line.Line;
import line.LineManager;
import line.Timetable;
import line.vehicle.Seat;
import line.vehicle.Train;
import line.vehicle.Vehicle;
import line.vehicle.Wagon;
import station.Station;
import station.StationManager;
import ticket.Passenger;
import ticket.Ticket;
import ticket.TicketManager;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Felhasználói (vásárlói) móddal kapcsolatos felület.
 */
public abstract class userUI {
    private static HashSet<Passenger> passengerList;
    /** Jelenleg belépett felhasználó */
    private static Passenger loggedInUser;

    private static final int EXIT = 0;
    private static final int PURCHASE_TICKET = 1;
    private static final int LIST_TICKET = 2;
    private static final int REFUND_TICKET = 3;
    private static final int SEARCH_TRAIN = 4;
    private static final int SEARCH_STATION_LINE = 5;

    /** Vásárlás mód opciói */
    public static final String[] MENU_BUY_MAIN = new String[]{
            "Kilépés",
            "Jegy vásárlása",
            "Jegyek listázása",
            "Jegy visszatérítése",
            "Vonatok keresése",
            "Állomást érintő vonatok keresése"
    };

    /**
     * Elindítja a felhasználói mód felületét.
     * @param passengers az összes beléptethető felhasználó listája
     */
    public static void start(HashSet<Passenger> passengers) {
        boolean exit = false;
        passengerList = passengers;
        loggedInUser = login();
        if(loggedInUser == null) return;


        while (!exit) {
            int option = standardUIMessage.selectMenu(MENU_BUY_MAIN);
            switch (option) {

                case PURCHASE_TICKET:
                    HashSet<Line> notEmptyLines = LineManager.getLines();
                    notEmptyLines.removeIf(l -> l.getVehicles().size() == 0);
                    if(!purchaseTicket(notEmptyLines)) System.out.println("A vásárlás nem sikerült.");
                    Save.save();
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
                    if(TicketManager.refund(selectTicket))
                        System.out.println("A kiválasztott jegy sikeresen visszafizetve.");
                    else
                        System.out.println("Nem sikerült a visszatérítés.");
                    Save.save();
                    standardUIMessage.ok();
                    break;

                case SEARCH_TRAIN:
                    Line selectedLine = listUI.selectLine(LineManager.getLines());
                    if(selectedLine == null) break;
                    ArrayList<Vehicle> sortVehicles = listUI.listVehicles(selectedLine, true, true);

                    System.out.print("Vonat sorszáma a menetrendhez: ");
                    int selected;
                    try{
                        selected = Integer.parseInt(Main.input.next())-1;
                    } catch (NumberFormatException e) { break; }
                    if(selected < 0 || selected > sortVehicles.size()) break;
                    System.out.println(selectedLine.getTimetable(sortVehicles.get(selected)));
                    standardUIMessage.ok();
                    break;

                case SEARCH_STATION_LINE:
                    listUI.listStations(StationManager.getStations(), false, true);
                    System.out.print("Állomás neve: ");
                    Station searchStation = StationManager.searchStation(Main.input.next());
                    if (searchStation == null) break;

                    Line[] foundLines = LineManager.searchLines(searchStation);
                    if (foundLines.length == 0) {
                        System.out.println("Ezen az állomáson 1 vonat se áll meg.");
                        break;
                    }
                    System.out.println(searchStation.getName() + " állomást a következő vonalak érintik:");
                    for (Line l : foundLines){
                        System.out.println(l.getName());
                    }
                    standardUIMessage.ok();

                    break;

                case EXIT: //Kilépés
                    exit = true;
                    break;
            }
        }

    }


    /**
     * Bejelentkeztet vagy regisztráltat egy felhasználót
     * @return a belépett felhasználó<br>
     * ha nem lépett be senki, {@code null}
     */
    private static Passenger login(){
        System.out.print("Felhasználónév: ");
        String name = Main.input.next();
        if (name.equals("") || name.equals("0")) return null;
        for (Passenger passenger : passengerList) {
            if (passenger.getName().equalsIgnoreCase(name)) {
                return passenger;
            }
        }

        System.out.println("Szeretnéd létrehozni \"" + name + "\" felhasználót?");
        if(standardUIMessage.yesNo()){
            Passenger registered = new Passenger(name);
            passengerList.add(registered);
            Save.save();
            return registered;
        }
        return null;
    }


    /**
     * A jegyvásárlás 1. része: a vásárló itt választja ki a vonatot,
     * induló és cél állomást, valamint hogy hány utasnak vesz jegyet.
     * @param lines kiírandó vonalak
     * @return {@code true}, ha sikeresen hozzáadta a jegyet a TicketManagerhez<br>
     * {@code false}, ha a vásárlás nem sikerült
     */
    public static boolean purchaseTicket(HashSet<Line> lines){
        //Vonal és vonat választás
        Line selectedLine = listUI.selectLine(lines);
        if (selectedLine == null) return false;
        Train selectedTrain;
        try {
             selectedTrain = (Train) listUI.selectVehicle(selectedLine, true);
        } catch (ClassCastException e){
            System.out.println("Ezzel a funkcióval csak vonatra lehet jegyet venni!");
            return false;
        }
        if(selectedTrain == null) return false;

        //Induló és cél állomás választás
        Timetable t = selectedLine.getTimetable(selectedTrain);
        System.out.println(t);
        System.out.print("Induló állomás? ");
        Station from = t.searchStation(Main.input.next());
        System.out.print("Cél állomás? ");
        Station to = t.searchStation(Main.input.next());
        if(from == null || to == null){
            System.out.println("A megadott állomás nem található.");
            standardUIMessage.ok();
            return false;
        }

        //Hány főnek
        System.out.print("Hány főnek szeretnél jegyet venni? ");
        int pplCount = Main.getInt();
        if(pplCount <= 0) return false;
        if(pplCount > selectedTrain.getTotalFreeSeatCount()){
            System.out.println("Sajnos nincs elég szabad ülés ezen a vonaton.");
            standardUIMessage.ok();
            return false;
        }

        //Automata/manuális hely
        System.out.println("(A)utomatikus vagy (k)ézi helyválasztás? A kézi helyválasztás felára: "
                + TicketManager.getManualSeatFee() + "Ft.");
        String manualS = Main.input.next().toLowerCase();
        boolean manual;
        if(manualS.contains("auto") || manualS.equals("a")){
            manual = false;
        } else if(manualS.contains("kéz") || manualS.equals("k")){
            manual = true;
        } else {
            return false;
        }

        //Vásárlás
        if (!manual){
            return autoBuy(from, to, selectedLine, selectedTrain, pplCount);
        } else {
            return manualBuy(from, to, selectedLine, selectedTrain, pplCount);
        }
    }

    /**
     * Automatikus jegyvásárló rendszer.<br>
     * Megveszi a kiválasztott vonatra a jegyet, a helyet automatikusan jelöli ki.
     *
     * @param from felszálló állomás
     * @param to leszálló állomás
     * @param line vonal
     * @param train vonat
     * @param pplCount hány főnek vegyen jegyet
     * @return {@code true}, ha sikeresen hozzáadta a jegyet a TicketManagerhez<br>
     * {@code false}, ha a vásárlás nem sikerült
     */
    public static boolean autoBuy(Station from, Station to, Line line, Train train, int pplCount) {
        if (train.getTotalFreeSeatCount() < pplCount) return false;

        Passenger passenger = loggedInUser;

        for (Wagon w : train.getWagons()) {

            for (int i = 0; i < w.getSeatCount(); i++) {
                if (w.getSeatStatus(i) == Seat.FREE) {
                    Ticket t = new Ticket(line, from, to, train, w.getSeat(i), passenger, false);
                    System.out.println(passenger.getName() + " jegye a " + t.getSeat().getSeatNumber() + " székre szól, " + t.getPrice() + "Ft.");
                    if(!TicketManager.purchase(t))
                        System.out.println("A vásárlás nem sikerült. Ez a személy már ül ezen a vonaton.");

                    Save.save();
                    pplCount--;
                    if(pplCount > 0) {
                        System.out.println("Kinek szól a következő jegy?");
                        do {
                            passenger = login();
                        } while (passenger == null);
                    } else return true;
                }
            }

        }

        return false;
    }

    /**
     * Manuális jegyvásárló rendszer.<br>
     * Ebben a módban a felhasználó maga választja ki, hova kéri a
     * helyjegyeket a vonaton, a még szabad székeken.
     *
     * @param from felszálló állomás
     * @param to leszálló állomás
     * @param line vonal
     * @param train vonat
     * @param pplCount hány főnek vegyen jegyet
     * @return {@code true}, ha sikeresen hozzáadta a jegyet a TicketManagerhez<br>
     * {@code false}, ha a vásárlás nem sikerült
     */
    public static boolean manualBuy(Station from, Station to, Line line, Train train, int pplCount) {
        Passenger passenger = loggedInUser;

        Wagon[] wagons = train.getWagons();

        while (pplCount > 0) {
            //Kocsi választás
            for (int i = 0; i < wagons.length; i++) {
                System.out.println(wagons[i]);
            }
            System.out.print("Válassz egy kocsit: ");
            int selectWagonI = Main.getInt()-1;
            if (selectWagonI >= wagons.length || selectWagonI < 0) {
                System.out.print("Biztos, hogy vissza szeretnél lépni? ");
                if (standardUIMessage.yesNo()) return false;
            }

            while (true) {
                //Szék választás
                Wagon selectW = wagons[selectWagonI];
                System.out.println("Szabad székek a " + selectW.getWagonNumber() + ". kocsiban:");
                for (int i = 0; i < selectW.getSeatCount(); i++) {
                    if (selectW.getSeatStatus(i) == Seat.FREE)
                        System.out.println(selectW.getSeat(i).getSeatNumber());
                }

                System.out.print("Szék neve (0: vissza a kocsi választáshoz): ");
                String selectSeatName = Main.input.next();
                if(selectSeatName.equals("0")) break;
                Seat selectedSeat = null;
                for (int i = 0; i < selectW.getSeatCount(); i++) {
                    if (selectW.getSeat(i).getSeatNumber().equalsIgnoreCase(selectSeatName)) {
                        selectedSeat = selectW.getSeat(i);
                    }
                }

                //Vásárlás
                if(selectedSeat == null) System.out.println("Nem található ilyen számú szék.");
                else {
                    Ticket t = new Ticket(line, from, to, train, selectedSeat, passenger, true);
                    System.out.println("Szerentéd megvenni a következő jegyet?\n" + t);
                    if(standardUIMessage.yesNo()){
                        if(!TicketManager.purchase(t)){
                            System.out.println("A vásárlás nem sikerült. Ez a személy már ül ezen a vonaton.");
                            return false;
                        }

                        Save.save();
                        System.out.println("Sikeres vásárlás.");
                        standardUIMessage.ok();
                        pplCount--;
                        if(pplCount > 0) {
                            System.out.println("Kinek szól a következő jegy?");
                            do {
                                passenger = login();
                            } while (passenger == null);
                        } else return true;
                    }
                }
            }

        }

        return false;
    }

}
