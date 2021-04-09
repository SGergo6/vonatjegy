package station;

public class StationNotFoundException extends RuntimeException {
    private String  stationName;
    private StationNotFoundException() {}

    public StationNotFoundException(String stationName) {
        super(stationName + " nevű állomás nem található.");
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }
}
