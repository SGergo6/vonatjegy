import IO.Input;

/** Különböző standard dolgokat ír ki a képernyőre. */
public abstract class standardUIMessage {

     /** Kiír egy ok üzenetet, és vár egy enterre.*/
    public static void ok(){
        System.out.println("Ok.");
        Input.nextLine();
    }

    /**
     * Egy igen/nem választ vár.
     * @return {@code true}, ha a válasz igen volt<br>
     * {@code false}, ha nem volt.
     */
    public static boolean yesNo(){
        String be = Input.nextLine();
        if(be.equalsIgnoreCase("y")
                || be.equalsIgnoreCase("yes")
                || be.equalsIgnoreCase("i")
                || be.equalsIgnoreCase("igen")) {
            return true;
        } else if(be.equalsIgnoreCase("n")
                || be.equalsIgnoreCase("nem")
                || be.equalsIgnoreCase("no")){
            return false;
        }
        System.out.println("Nem értelmezhető válasz, igen/nem a várt válasz.");
        return yesNo();
    }

    /** Egy számot vár a felhasználótól a 2 megadott szám között.*/
    public static int szam_be(int min, int max){
        if(min > max){
            int tmp = min;
            min = max;
            max = tmp;
        }

        int be = Input.getInt("Egész számot kell megadni");
        while (be < min || be > max){
            System.out.println("Érvénytelen szám, " + min + " és " + max + " között kell lennie.");
            be = Input.getInt("Egész számot kell megadni");
        }

        return be;
    }

    /**
     * Kiírja az összes megadott elemet
     * @param items kiírandó elemek
     * @param printIndex ha {@code true}, a szöveg elé írja a sorszámát is (menüpontokhoz, sorszámos listázáshoz)
     */
    public static void printMenu(String[] items, boolean printIndex){
        for (int i = 0; i < items.length; i++) {
            if(printIndex) System.out.print(i + ". ");
            System.out.println(items[i]);
        }
    }

    /**
     * Kiírja az összes megadott elemet, és visszatér a felhasználó által választott sorszámmal.
     * @param items kiírandó elemek
     * @return a felhasználó által megadott index
     */
    public static int printMenu(String[] items){
        for (int i = 0; i < items.length; i++) {
            System.out.println(i + ". " + items[i]);
        }
        return szam_be(0, items.length);
    }


}
