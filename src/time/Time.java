package time;

import java.io.Serializable;

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
    }

     /** Létrehoz egy idő osztályt, az óra:percet stringként megadva. */
    public Time(String time){
        String[] timeSpl = time.split(":");
        this.min = Integer.parseInt(timeSpl[1]);
        if(min>=60){
            throw new IllegalArgumentException("A perc túllépte a maximális 60 percet!");
        }
        this.min += 60 * Integer.parseInt(timeSpl[0]);
        if(this.min >= 1440){
            throw new IllegalArgumentException("A megadott idő több, mint 23:59!");
        }
    }

    /** Létrehoz egy idő osztályt, amiben a megadott számot adja meg a belső órába. */
    Time(int mins){
        this.min = mins;
    }

    /** Létrehoz egy idő osztályt, 0:00 értékkel. */
    public Time() {
        this.min = 0;
    }

    /** Visszadja a bent lévő órát */
    public int getHours(){
        return (int)Math.floor((double)this.min/60);
    }

    /** Visszaadja az óra percét */
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


    public Time add(Time t) throws TimeOverflowException{
        return addMins(t.getMinsOnly());
    }
    public Time addHours(int addH) throws TimeOverflowException{
        return addMins(addH*60);
    }
    public Time addMins(int addMin) throws TimeOverflowException{
        int retMins = this.min;
        retMins += addMin;
        if (retMins >= 1440){
            throw new TimeOverflowException();
        }
        return new Time(retMins);
    }

    public Time sub(Time t) throws TimeUnderflowException{
        return subMins(t.getMinsOnly());
    }
    public Time subHours(int subH) throws TimeUnderflowException{
        return subMins(subH*60);
    }
    public Time subMins(int subMin) throws TimeUnderflowException{
        int retMins = this.min;
        retMins -= subMin;
        if(retMins < 0){
            throw new TimeUnderflowException();
        }
        return new Time( retMins);
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
     * @param t1 az tartomány alsó fele (beleértve) (megcserélhető a 2 érték)
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
}
