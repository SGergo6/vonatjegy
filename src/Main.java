import IO.Input;
import station.Station;
import station.StationManager;
import ticket.Passenger;
import ticket.TicketManager;
import line.Line;
import line.LineManager;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        initializeProgram();

        StationManager.newStation("Keleti");
        StationManager.newStation("Nyugati");
        StationManager.newStation("Déli");

        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger("Béla"));
        passengers.add(new Passenger("Pista"));
        passengers.add(new Passenger("Jenő"));

        Station[] route = new Station[]{StationManager.searchStation("keleti"), StationManager.searchStation("déli")};

        Line line = new Line(route, "S70", 100);
        line.newTrain(5);


    }

    /**
     * Inicializálja az összes inicializálandó osztályt.
     */
    public static void initializeProgram(){
        Input.initialize(System.in);
        StationManager.initialize();
        TicketManager.initialize();
        LineManager.initialize();
    }
}
