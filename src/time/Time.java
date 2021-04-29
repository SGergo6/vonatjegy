package time;

import java.io.Serializable;
import java.util.Objects;

/** 24 órás intervallumot eltároló osztály. */
public class Time implements Comparable<Time>, Serializable {
    /** 0 órától eltelt percek száma. */
    private int min;

    /** Létrehoz egy idő osztályt, megadva az órát és a percet. */
    public Time(int hour, int min){
        if(min>=60){
            throw new IllegalArgumentException("A perc túllépte a maximális 60 percet!");
        }
        this.min = min;
        this.min += 60 * hour;
        checkTime(this.min);
    }

     /** Létrehoz egy idő osztályt.
      * @param time óra:perc stringként megadva.
      */
    public Time(String time) {
        String[] timeSpl = time.split(":");
        if (timeSpl.length < 2) throw new IllegalArgumentException("A megadott idő nem értelmezhető!");
        this.min = Integer.parseInt(timeSpl[1]);
        if (min >= 60) {
            throw new IllegalArgumentException("A perc túllépte a maximális 60 percet!");
        }
        this.min += 60 * Integer.parseInt(timeSpl[0]);
        if (this.min >= 1440) {
            throw new IllegalArgumentException("A megadott idő több, mint 23:59!");
        }
    }

    /** Létrehoz egy idő osztályt, amiben a megadott számot adja meg a belső órába. */
    Time(int mins){
        checkTime(mins);
        this.min = mins;
    }

    /** Létrehoz egy idő osztályt, 0:00 értékkel. */
    public Time() {
        this.min = 0;
    }

    /** Visszadja az eltárolt órát */
    public int getHours(){
        return (int)Math.floor((double)this.min/60);
    }

    /** Visszaadja az eltárolt óra percét */
    public int getMins(){
        int retMin = this.min;
        while(retMin >= 60){
            retMin -= 60;
        }
        return retMin;
    }

    /** Visszaadja a belső {@code min} változó értékét. */
    public int getMinsOnly(){
        return min;
    }


    /**
     * Hozzáad egy másik idő osztályt ehhez az időhöz.
     * @param t hozzáadandó idő
     * @return Az idő osztályt, amiben már össze van adva a 2 érték.
     * @throws TimeException ha az összeadás során 24 óra fölé vagy 0 óra alá ment az idő.
     */
    public Time add(Time t) throws TimeException{
        return addMins(t.getMinsOnly());
    }
    /**
     * Hozzáad ehhez az időhöz megadott számú órát.
     * @param addH hozzáadandó óra
     * @return Az idő osztályt, amiben már össze van adva a 2 érték.
     * @throws TimeException ha az összeadás során 24 óra fölé vagy 0 óra alá ment az idő.
     */
    public Time addHours(int addH) throws TimeException{
        return addMins(addH*60);
    }
    /**
     * Hozzáad megadott mennyiségű percet ehhez az időhöz.
     * @param addMin hozzáadandó idő
     * @return Az idő osztályt, amiben már össze van adva a 2 érték.
     * @throws TimeException ha az összeadás során 24 óra fölé vagy 0 óra alá ment az idő.
     */
    public Time addMins(int addMin) throws TimeException{
        int retMins = this.min;
        retMins += addMin;
        checkTime(retMins);
        return new Time(retMins);
    }

    /**
     * Kivon egy másik idő osztályt ebből.
     * @param t kivonandó idő
     * @return Az idő osztályt, amiben már ki van vonva a 2 érték.
     * @throws TimeException ha az kivonás során 24 óra fölé vagy 0 óra alá ment az idő.
     */
    public Time sub(Time t) throws TimeException{
        return subMins(t.getMinsOnly());
    }
    /**
     * Kivon ebből az időből megadott számú órát.
     * @param subH kivonandó óra száma
     * @return Az idő osztályt, amiben már ki van vonva a 2 érték.
     * @throws TimeException ha az kivonás során 24 óra fölé vagy 0 óra alá ment az idő.
     */
    public Time subHours(int subH) throws TimeException{
        return subMins(subH*60);
    }
    /**
     * Kivon ebből az időből megadott számú percet.
     * @param subMin kivonandó percek
     * @return Az idő osztályt, amiben már ki van vonva a 2 érték.
     * @throws TimeException ha az kivonás során 24 óra fölé vagy 0 óra alá ment az idő.
     */
    public Time subMins(int subMin) throws TimeException{
        int retMins = this.min;
        retMins -= subMin;
        checkTime(retMins);
        return new Time( retMins);
    }

    /**
     * Ellenőrzi, hogy a megadott idő benne van-e a 24 órás időtartományban.
     * @param minutes ellenőrizendő perc
     * @throws TimeException ha hibás volt a megadott perc
     */
    private void checkTime(int minutes) throws TimeException{
        if (minutes >= 1440){
            throw new TimeOverflowException();
        }
        if(minutes < 0){
            throw new TimeUnderflowException();
        }
    }


    public boolean greaterThan(Time t){
        return this.getMinsOnly() > t.getMinsOnly();
    }

    public boolean lessThan(Time t){
        return this.getMinsOnly() < t.getMinsOnly();
    }

    public boolean greaterOrEqualsThan(Time t){
        return this.getMinsOnly() >= t.getMinsOnly();
    }

    public boolean lessOrEqualsThan(Time t){
        return this.getMinsOnly() <= t.getMinsOnly();
    }

    @Override
    public int compareTo(Time t){
        return Integer.compare(this.min, t.getMinsOnly());
    }

    public String toString() {
        int perc = getMins();
        //Ha 10 perc alatt van, akkor 0-t tesz a szám elé, pl: 10:*05*
        return getHours() + ":" + (perc >= 10 ? perc : ("0" + perc));
    }

    /**
     * Ellenőrzi, hogy ez az idő benne van-e egy megadott időtartományban, vagy sem.
     * @param t1 az tartomány alsó fele (beleértve) <i>(megcserélhető a 2 érték)</i>
     * @param t2 az tartomány felső fele (beleértve)
     * @return {@code true}, ha benne van a tartományban.
     */
    public boolean interval(Time t1, Time t2){
        if(t1.greaterThan(t2)) {
            Time tmp = t1;
            t1 = t2;
            t2 = tmp;
        }
        return this.greaterOrEqualsThan(t1) && this.lessOrEqualsThan(t2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return this.min == time.min;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min);
    }
}
