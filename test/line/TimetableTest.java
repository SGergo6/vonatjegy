package line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.Station;
import station.StationManager;
import time.Time;

import static org.junit.jupiter.api.Assertions.*;

public class TimetableTest {
    @BeforeEach
    public void setUp(){
        StationManager.initialize();
        LineManager.initialize();

        StationManager.newStation("Keleti");
        StationManager.newStation("Nyugati");
        StationManager.newStation("Déli");
        StationManager.newStation("Újpest");

        Station[] route = new Station[]{
                StationManager.searchStation("keleti"),
                StationManager.searchStation("nyugati"),
                StationManager.searchStation("déli")
        };

        LineManager.addLine(route, "S70", 70);
    }

    @Test
    void testTimetable() {
        Timetable t1 = timetableCreator(LineManager.searchLine("S70"));
        Time[] expectedTimes = new Time[]{
                new Time(10, 0),
                new Time(10, 10),
                new Time(10, 20)
        };

        assertFalse(t1.addTime(StationManager.searchStation("újpest"), new Time(10,30)),
                "Hozzáadás rossz állomáshoz");
        assertNull(t1.getStationArrival(StationManager.searchStation("újpest")),
                "Rossz állomás idő lekérdezése");
        assertEquals(expectedTimes[0], t1.getStationArrival(new Station("keleti")), "Keleti idő lekérdezés");
        assertEquals(expectedTimes[1], t1.getStationArrival(new Station("nyugati")), "Nyugati idő lekérdezés");
        assertEquals(expectedTimes[2], t1.getStationArrival(new Station("déli")), "Déli idő lekérdezés");


        assertArrayEquals(t1.getArrivals(), expectedTimes);

        assertNull(t1.searchStation("Mucsaröcsöge"), "Keresés nem létezőre");
        assertNull(t1.searchStation("újpest"), "Keresés nem vonalon lévőre");
        assertEquals(StationManager.searchStation("déli"), t1.searchStation("dél"), "Keresés vonalon lévőre");
    }

    @Test
    void testLineTimetable() {
        Line line_s70 = LineManager.searchLine("s70");
        Timetable t1 = timetableCreator(line_s70);

        assertNotNull(line_s70);
        line_s70.newTrain(1, 10, Timetable.DIRECTION_NORMAL, t1);

        assertEquals(t1, line_s70.getTimetable(line_s70.findVehicle(new Time(10,0))));
        assertEquals(line_s70, t1.getLine());


    }

    /**
     * Létrehoz egy új menetrendet, a menetrend 10 órán áll, a perc az állomás indexe*10.
     */
    public static Timetable timetableCreator(Line line){
        Timetable t = new Timetable(line);

        for (int i = 0; i < t.Size(); i++){
            //Minden megállóra 10 óra i*10 perckor érkezik
            t.addTime(t.getStations()[i], new Time(10,i*10));
        }

        return t;
    }
}
