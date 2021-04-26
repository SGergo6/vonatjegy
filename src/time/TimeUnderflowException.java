package time;

public class TimeUnderflowException extends TimeException {
    public TimeUnderflowException(){
        super("A művelet alullépte a minimális 0 órát.");
    }
}
