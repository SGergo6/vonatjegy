package ticket;

import line.*;
import line.vehicle.Train;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.Station;
import station.StationManager;
import time.Time;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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

        LineManager.addLine(new Station[]{
                StationManager.searchStation("Keleti"),
                        StationManager.searchStation("Nyugati"),
                        StationManager.searchStation("Déli")
                },
                "S70", 150);

        LineManager.searchLine("s70").newTrain(2, 10, Timetable.DIRECTION_NORMAL,
                TimetableTest.timetableCreator(LineManager.searchLine("s70")));

    }

    @Test
    void PurchaseTest() {

        Line selectedLine = LineManager.searchLine("S70");
        Train train = (Train) selectedLine.findVehicle(new Time("10:00"));

        TicketManager.purchase(new Ticket(
                selectedLine,
                StationManager.searchStation("keleti"),
                StationManager.searchStation("nyugati"),
                train,
                train.getSeat(0,5),
                passengers.get(0),
                false
        ));


        assertEquals(1, TicketManager.getTickets().size(), "A ticketmanager elmentette a jegyet");
        assertTrue(train.getSeat(0,5).getStatus(), "Az ülés foglalt lett");
        assertFalse(train.getSeat(1,5).getStatus(), "Egy másik kocsi ugyanolyan indexű eleme nem foglalt");
        assertEquals(1, passengers.get(0).getTickets().size(), "Utas jegyei között megjelent");
        assertEquals(-150, passengers.get(0).balance, "Egyenleg teszt");

        assertThrows(SeatOccupiedException.class, ()->
                TicketManager.purchase(new Ticket(
                        selectedLine,
                        StationManager.searchStation("keleti"),
                        StationManager.searchStation("nyugati"),
                        train,
                        train.getSeat(0,5),
                        passengers.get(1),
                        false
                )),
                "Foglalt ülésre próbál vásárolni"
        );

        assertThrows(IllegalArgumentException.class, ()->
                TicketManager.purchase(new Ticket(
                        selectedLine,
                        StationManager.searchStation("nyugati"),
                        StationManager.searchStation("déli"),
                        train,
                        train.getSeat(0,4),
                        passengers.get(0),
                        false
                )),
                "Ugyanaz az utas mégegyszer vásárol a vonatra"
        );
        assertFalse(train.getSeat(0,4).getStatus(),
                "Az előző vásárlás nem sikerült, az ülésnek szabadnak kell lennie");


        //Visszautalás teszt

        TicketManager.refund(TicketManager.findTickets(passengers.get(0), selectedLine).get(0));

        assertEquals(0, TicketManager.getTickets().size(), "A ticketmanager kitörölte a jegyet");
        assertFalse(train.getSeat(0,5).getStatus(), "Az ülés felszabadult");
        assertEquals(0, passengers.get(0).getTickets().size(), "Utas jegyei közül kitörlődött");
        assertEquals(0, passengers.get(0).balance, "Egyenleg teszt");

    }
}
