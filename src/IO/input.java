package IO;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Statikus scanner osztály, használat előtt inicializálni kell az {@code initialize()} metódussal.<br>
 * <br>
 * <b>String kérése:</b> {@code nextLine()} metódussal<br>
 * <b>Int/double javasolt kérése:</b> {@code getInt/Double(hibaüzenet)} metódussal
 */
public abstract class input {
    private static Scanner input;
    private static boolean initialized;

    /**
     * Inicializálja a scannert.
     * @param source a bemenet forrása ({@code System.in}, ha a felhasználótól vársz adatot)
     */
    public static void initialize(InputStream source){
        if(!initialized){
            input = new Scanner(source);
            initialized = true;
        }
    }

    /**
     * Bezárja a Scannert.
     */
    public static void close(){
        if(initialized){
            input.close();
            initialized = false;
        }
    }


    /**
     * Kér a felhasználótól egy {@code int} értéket, addig próbálkozik, ameddig nem jár sikerrel.
     * @param errorMsg ezt a megadott üzenetet írja ki hiba esetén a felhasználónak, mielőtt újrakéri az értéket.
     * @return a felhasználótól kapott {@code int}.
     * @throws InputNotInitializedException hibát dob, ha nem volt inicializálva a {@code Scanner}.
     */
    public static int getInt(String errorMsg) throws InputNotInitializedException{
        while (true){
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                input.next();
                System.out.println(errorMsg);
            }
        }
    }

    /**
     * Kér a felhasználótól egy {@code double} értéket, addig próbálkozik, ameddig nem jár sikerrel.
     * @param errorMsg ezt a megadott üzenetet írja ki hiba esetén a felhasználónak, mielőtt újrakéri az értéket.
     * @return a felhasználótól kapott {@code int}.
     * @throws InputNotInitializedException hibát dob, ha nem volt inicializálva a {@code Scanner}.
     */
    public static double getDouble(String errorMsg) throws InputNotInitializedException{
        while (true){
            try {
                double in;
                in = input.nextDouble();
                return in;
            } catch (InputMismatchException e) {
                input.next();
                System.out.println(errorMsg);
            }
        }
    }


    /**
     * Meghívja a {@code Scanner nextLine()} metódusát, és visszaadja a kapott értéket.
     * @return A felhasználótól kapott {@code String}-et.
     * @throws InputNotInitializedException hibát dob, ha nem volt inicializálva a {@code Scanner}.
     */
    public static String nextLine() throws InputNotInitializedException{
        checkInput();
        return input.next();
    }

    /**
     * Meghívja a {@code Scanner nextInt()} metódusát, és visszaadja a kapott értéket.
     * @return A felhasználótól kapott {@code int} értéket.
     * @throws InputNotInitializedException hibát dob, ha nem volt inicializálva a {@code Scanner}.
     * @throws InputMismatchException hibát dob, ha a felhasználó hibás adatot adott meg.
     */
    public static int nextInt() throws InputNotInitializedException, InputMismatchException{
        checkInput();
        return input.nextInt();
    }

    /**
     * Meghívja a {@code Scanner nextDouble()} metódusát, és visszaadja a kapott értéket.
     * @return A felhasználótól kapott {@code double} értéket.
     * @throws InputNotInitializedException hibát dob, ha nem volt inicializálva a {@code Scanner}.
     * @throws InputMismatchException hibát dob, ha a felhasználó hibás adatot adott meg.
     */
    public static double nextDouble() throws InputNotInitializedException, InputMismatchException {
        checkInput();
        return input.nextDouble();
    }

    /**
     * Ellenőrzi, hogy volt-e már inicializálva a scanner.
     * @throws InputNotInitializedException hibát dob, amennyiben nem volt inicializálva korábban.
     */
    private static void checkInput() throws InputNotInitializedException {
        if(!initialized){
            throw new InputNotInitializedException();
        }
    }

}
