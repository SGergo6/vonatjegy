package time;

public class TimeUnderflowException extends RuntimeException{
    public TimeUnderflowException(){
        super("A művelet alullépte a minimális 0 órát.");
    }
}
