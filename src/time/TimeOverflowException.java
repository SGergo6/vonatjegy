package time;

public class TimeOverflowException extends RuntimeException {
    public TimeOverflowException(){
        super("A művelet túllépte a maximásils 24 órát");
    }
}
