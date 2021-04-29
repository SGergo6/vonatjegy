package line;

import line.comparator.LineNameComparator;
import line.vehicle.Train;
import line.vehicle.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.Station;
import station.StationManager;
import time.Time;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LineTest {
    @BeforeEach
    public void setUp(){
        StationManager.initialize();
        LineManager.initialize();

        StationManager.newStation("Keleti");
        StationManager.newStation("Nyugati");
        StationManager.newStation("Déli");
        StationManager.newStation("Újpest");

        Station[] route1 = new Station[]{
                StationManager.searchStation("keleti"),
                StationManager.searchStation("nyugati"),
                StationManager.searchStation("déli")
        };
        Station[] route2 = new Station[]{
                StationManager.searchStation("déli"),
                StationManager.searchStation("újpest"),
                StationManager.searchStation("keleti")
        };

        LineManager.addLine(route1, "S70", 70);
        Line l = new Line(route2, "Z80", 100);
        LineManager.addLine(l);
    }

    @Test
    void testLines() {
        assertNull(LineManager.searchLine("s71"), "Keresés rossz névre");
        Line s70 = LineManager.searchLine("s70");
        assertNotNull(s70, "Vonal keresés");


        assertEquals(StationManager.searchStation("keleti"), s70.getDeparture(), "Induló állomás megegyezik");
        assertEquals(StationManager.searchStation("déli"), s70.getArrival(), "Érkezési állomás megegyezik");
        assertEquals(1, LineManager.searchLines(StationManager.searchStation("újpest")).length);
        assertEquals(2, LineManager.searchLines(StationManager.searchStation("déli")).length);
    }


    @Test
    void testTrains(){
        Line s70 = LineManager.searchLine("s70");
        assertNotNull(s70);
        assertEquals(0, s70.getVehicles().size(), "Még nincs vonat a vonalon");

        Timetable table = new Timetable(s70);
        table.addTime(StationManager.searchStation("keleti"), new Time("6:00"));
        table.addTime(StationManager.searchStation("nyugati"), new Time("7:00"));
        table.addTime(StationManager.searchStation("déli"), new Time("8:00"));

        s70.newTrain(3, 10, Timetable.DIRECTION_NORMAL, table);

        Train train = (Train) s70.findVehicle(new Time(6,0));

        assertEquals(1, s70.getVehicles().size(), "Hozzáadva 1 vonat");
        assertEquals(table, s70.getTimetable(train),
                "A vonat lekérdezett menetrendje megegyezik az előbb létrehozottal");

        assertNull(s70.findVehicle(new Time(0,0)), "Random időre nem talál semmit");
        assertEquals(1, s70.findVehicles(new Time(6,30), 45).length,
                "Intervallumos keresés, találat");
        assertEquals(0, s70.findVehicles(new Time(6,30), 25).length,
                "Intervallumos keresés, nincs találat");

        assertEquals(30, train.getTotalSeatCount());
        assertEquals(30, train.getTotalFreeSeatCount());
        train.reserveSeat(0,0);
        assertEquals(30, train.getTotalSeatCount());
        assertEquals(29, train.getTotalFreeSeatCount());
        assertTrue(train.getSeat("1-1").getStatus());
        assertFalse(train.getSeat("2-1").getStatus());
        assertFalse(train.getSeat("1-2").getStatus());
        assertNull(train.getSeat("0-0"));

        train.freeSeat(0,0);
        assertEquals(30, train.getTotalSeatCount());
        assertEquals(30, train.getTotalFreeSeatCount());
    }


    @Test
    public void testNameComparator(){
        ArrayList<Line> sorted = new ArrayList<>(LineManager.getLines());
        sorted.sort(new LineNameComparator());

        assertEquals("S70", sorted.get(0).getName(), "ABC renzezés");
        assertEquals("Z80", sorted.get(1).getName(), "ABC renzezés");
    }
}
