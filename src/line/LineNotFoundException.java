package line;

public class LineNotFoundException extends RuntimeException {
    public LineNotFoundException(){
        super("A keresett vonal nem található.");
    }
}
