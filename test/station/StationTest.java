package station;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StationTest {
    @BeforeEach
    public void setUp(){
        StationManager.initialize();
    }

    @Test
    public void testDuplicates(){
        StationManager.newStation("Keleti");
        StationManager.newStation("Nyugati");
        assertEquals(2, StationManager.getStations().size(),
                "Hozzáadva 2 külön állomás, a mérete 2 kell legyen");
        StationManager.newStation("Nyugati");
        assertEquals(2, StationManager.getStations().size(),
                "Hozzáadva újra egy ugyanolyan nevű állomás, a mérete még mindig 2 kell legyen");
        StationManager.newStation("nyugati");
        assertEquals(2, StationManager.getStations().size(),
                "Hozzáadva újra egy ugyanolyan nevű állomás, kisbetűkkel, a mérete még mindig 2 kell legyen");

        Station keleti = new Station("Keleti");
        Station nyugati = new Station("nyugati");

        StationManager.newStation(keleti);
        assertEquals(2, StationManager.getStations().size(),
                "Hozzáadva egy ugyanolyan nevű állomás osztályból, a mérete még mindig 2 kell legyen");
        StationManager.newStation(nyugati);
        assertEquals(2, StationManager.getStations().size(),
                "Hozzáadva egy ugyanolyan nevű állomás osztályból, kisbetűkkel, a mérete még mindig 2 kell legyen");

    }

    @Test
    void testSearch() {
        StationManager.newStation("Keleti");
        StationManager.newStation("Nyugati");

        Station keleti = new Station("Keleti");
        Station nyugati = new Station("nyugati");

        assertEquals(keleti, StationManager.searchStation("Keleti"),
                "Nagybetű-egyezéses keresés");
        assertEquals(nyugati, StationManager.searchStation("nyugati"),
                "Nem egyező nagybetűs keresés");
        assertNull(StationManager.searchStation("Déli"), "Nem létező állomás keresése");


    }
}
