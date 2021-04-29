package time;

/** Exception arra az esetre, ha az idő osztály 24 óránál több időt kellene eltároljon. */
public class TimeOverflowException extends TimeException {
    /** Létrehoz egy alap {@code TimeOverflowException}-t, egy sablon szöveggel. */
    public TimeOverflowException(){
        super("A művelet túllépte a maximásils 24 órát");
    }
}
