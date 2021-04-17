package IO;

public class LoadFailed extends Error{
    public LoadFailed(Throwable throwable){
        super("A betöltés nem sikerült egy hibás fájl miatt.", throwable);
    }
}
