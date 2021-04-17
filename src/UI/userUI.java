package UI;

public abstract class userUI {
    private static final int EXIT = 0;
    private static final int SELECT_STATION = 1;
    private static final int PURCHASE_TICKET = 2;
    private static final int LIST_TICKET = 3;
    private static final int REFUND_TICKET = 4;
    private static final int SEARCH_TRAIN = 5;

    /** Vásárlás mód opciói */
    public static final String[] MENU_BUY_MAIN = new String[]{
            "Kilépés",
            "Induló állomás beállítása",
            "Jegy vásárlása",
            "Jegyek listázása",
            "Jegy visszatérítése",
            "Vonatok keresése"

    };

    public static void start() {
        boolean exit = false;

        while (!exit) {
            int option = standardUIMessage.printMenu(MENU_BUY_MAIN);
            switch (option) {

                case SELECT_STATION:
                    break;

                case PURCHASE_TICKET:
                    break;

                case LIST_TICKET:
                    break;

                case REFUND_TICKET:
                    break;

                case SEARCH_TRAIN: //Vonatok keresése
                    break;

                case EXIT: //Kilépés
                    exit = true;
                    break;
            }
        }
    }
}
