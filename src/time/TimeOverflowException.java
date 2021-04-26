package time;

public class TimeOverflowException extends TimeException {
    public TimeOverflowException(){
        super("A művelet túllépte a maximásils 24 órát");
    }
}
