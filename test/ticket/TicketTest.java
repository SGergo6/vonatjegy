package ticket;

import line.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.Station;
import station.StationManager;
import line.LineManager;

import java.util.ArrayList;

public class TicketTest {
    ArrayList<Passenger> passengers;
    @BeforeEach
    void setUp() {
        TicketManager.initialize();
        StationManager.initialize();
        LineManager.initialize();

        StationManager.newStation("Keleti");
        StationManager.newStation("Nyugati");
        StationManager.newStation("Déli");

        passengers = new ArrayList<>();
        passengers.add(new Passenger("Béla"));
        passengers.add(new Passenger("Pista"));
        passengers.add(new Passenger("Jenő"));

        LineManager.addLine(new Station[]{StationManager.searchStation("Keleti"), StationManager.searchStation("Nyugati")},
                "S70", 150);

    }

    @Test
    void PurchaseTest() {

        /*Line selectedLine = LineManager.searchLine("S70");

        TicketManager.purchase(new Ticket(
                selectedLine,
                StationManager.searchStation("keleti"),
                StationManager.searchStation("nyugati"),

        ));*/
    }
}
