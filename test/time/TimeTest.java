package time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimeTest {
    Time t1;
    Time t2;
    Time t3;
    Time t4;

    @BeforeEach
    void setUp() {
        t1 = new Time(11, 0);
        t2 = new Time(11, 30);
        t3 = new Time(12, 0);
        t4 = new Time("13:45");
    }

    @Test
    void BasicTest() {
        assertEquals("11:00", t1.toString(), "String teszt: 11:00");
        assertEquals("11:30", t2.toString(), "String teszt: 11:30");
        assertEquals("12:00", t3.toString(), "String teszt: 12:00");
        assertEquals("13:45", t4.toString(), "String teszt: 13:45");
        assertEquals(t1, t3.sub(new Time(1,0)), "Kivonás teszt: 12:00 - 1 óra");
        assertEquals(t1, t3.subHours(1), "Kivonás teszt: 12:00 - 1 óra");
        assertEquals(t1, t3.subMins(60), "Kivonás teszt: 12:00 - 1 óra");
        assertEquals(t2, t3.sub(new Time(0,30)), "Kivonás teszt: 12:00 - 30 perc");
        assertEquals(t2, t3.subMins(30), "Kivonás teszt: 12:00 - 30 perc");
        assertEquals(t3, t1.add(new Time(1,0)), "Összadás teszt: 11:00 + 1 óra");
        assertEquals(t3, t1.addHours(1), "Összadás teszt: 11:00 + 1 óra");
        assertEquals(t3, t1.addMins(60), "Összadás teszt: 11:00 + 1 óra");
        assertEquals(t2, t1.add(new Time(0,30)), "Összadás teszt: 11:00 + 30 perc");
        assertEquals(t2, t1.addMins(30), "Összadás teszt: 11:00 + 30 perc");

        assertEquals(0, t1.compareTo(t1), "Összehasonlítás teszt: saját magán hívva");
        assertEquals(1, t2.compareTo(t1), "Összehasonlítás teszt: 11:30 - 11:00 között");
        assertEquals(-1, t1.compareTo(t2), "Összehasonlítás teszt: 11:00 - 11:30 között");
    }

    @Test
    void IntervalTest() {
        assertTrue(t2.interval(t1, t3), "Intervallum teszt: 11:30 11:00 és 12:00 között van-e");
        assertTrue(t2.interval(t3, t1), "Intervallum teszt: 11:30 11:00 és 12:00 között van-e, fordított sorrend");
        assertTrue(t1.interval(t1, t3), "Alsó határ teszt: 11:00 11:00 és 12:00 között van-e");
        assertTrue(t3.interval(t1, t3), "Felső határ teszt: 12:00 11:00 és 12:00 között van-e");
        assertFalse(t1.interval(t2,t3), "Intervallum teszt: 11:00 11:30 és 12:00 között van-e");
        assertFalse(t1.interval(t3,t2), "Intervallum teszt: 11:00 11:30 és 12:00 között van-e, fordított sorrend");
    }

    @Test
    void OverflowTest(){
        Time t5 = new Time(23, 0);
        Time t6 = new Time(1, 0);

        assertThrows(TimeUnderflowException.class, ()->t6.sub(new Time(2,0)), "Kivonás teszt: 1:00 - 2 óra");
        assertThrows(TimeUnderflowException.class, ()->t6.subMins(120), "Kivonás teszt: 1:00 - 2 óra");
        assertThrows(TimeUnderflowException.class, ()->t6.subHours(2), "Kivonás teszt: 1:00 - 2 óra");

        assertThrows(TimeOverflowException.class, ()->t5.add(new Time(2,0)), "Összadás teszt: 23:00 + 2 óra");
        assertThrows(TimeOverflowException.class, ()->t5.addMins(120), "Összadás teszt: 23:00 + 2 óra");
        assertThrows(TimeOverflowException.class, ()->t5.addHours(2), "Összadás teszt: 23:00 + 2 óra");

    }
}
