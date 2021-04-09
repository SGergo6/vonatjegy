package line;

import station.Station;

public class RouteException extends RuntimeException{
    Line line;
    Station station;

    public RouteException(Line line, Station station){
        super("A megadott megálló (\"" + station + "\") ezen a vonalon (\""
                + line.getName() + "\") nem érhető el.");
        this.line = line;
        this.station = station;
    }

    private RouteException(){ }
    private RouteException(String message, Throwable cause) { }
    private RouteException(Throwable cause) { }
    private RouteException(String message){ }

    public Line getLine() {
        return line;
    }

    public Station getStation() {
        return station;
    }
}
