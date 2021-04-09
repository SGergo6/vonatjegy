package IO;

/**
 * Futásidejű hiba, akkor dobódik, ha az inputot inicializálás nélkül használják.
 */
class InputNotInitializedException extends RuntimeException{
    public InputNotInitializedException() {
        super("Használat előtt inicializálni kell az inputot!");
    }
}
