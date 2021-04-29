package time;

/** Exception arra az esetre, ha az idő osztály 0 óránál kevesebb időt kellene eltároljon. */
public class TimeUnderflowException extends TimeException {
    /** Létrehoz egy alap {@code TimeUnderflowException}-t, egy sablon szöveggel. */
    public TimeUnderflowException(){
        super("A művelet alullépte a minimális 0 órát.");
    }
}
