package line;

import IO.Input;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import station.Station;
import station.StationManager;
import ticket.TicketManager;
import time.Time;

import java.util.Arrays;

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
        Timetable t1 = new Timetable(LineManager.searchLine("S70"));

        for (int i = 0; i < t1.Size(); i++){
            //Minden megállóra 10 óra i*10 perckor érkezik
            t1.addTime(t1.getStations()[i], new Time(10,i*10));
        }
        Time[] expectedTimes = new Time[]{
                new Time(10, 0),
                new Time(10, 10),
                new Time(10, 20)
        };

        assertThrows(RouteException.class, ()-> t1.getStationArrival(StationManager.searchStation("újpest")));
        assertEquals(t1.getStationArrival(new Station("keleti")), expectedTimes[0]);
        assertEquals(t1.getStationArrival(new Station("nyugati")), expectedTimes[1]);
        assertEquals(t1.getStationArrival(new Station("déli")), expectedTimes[2]);

        assertArrayEquals(t1.getArrivals(), expectedTimes);
    }

    @Test
    void testLineTimetable() {
        Line line_s70 = LineManager.searchLine("s70");
        Timetable t1 = new Timetable(line_s70);


        for (int i = 0; i < t1.Size(); i++){
            //Minden megállóra 10 óra i*10 perckor érkezik
            t1.addTime(t1.getStations()[i], new Time(10,i*10));
        }

        line_s70.newTrain(1, 10, Timetable.DIRECTION_NORMAL, t1);

        assertEquals(t1, line_s70.getTimetable(line_s70.findTrain(new Time(10,0))));


    }
}
