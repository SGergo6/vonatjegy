package IO;

/** Betöltés közbeni kritikus hibát jelez. */
public class LoadFailed extends Error{
    public LoadFailed(Throwable throwable){
        super("A betöltés nem sikerült egy hibás fájl miatt.", throwable);
    }
}
