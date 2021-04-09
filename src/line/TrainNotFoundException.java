package line;

public class TrainNotFoundException extends RuntimeException {
    public TrainNotFoundException(){
        super("A keresett vonat nem található.");
    }
}
