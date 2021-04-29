package time;

/** Összefoglaló osztály az időhöz kapcsolódó exceptionokhoz. */
public abstract class TimeException extends RuntimeException{
    public TimeException(String message){
        super(message);
    }
}
