package time;

import java.util.Scanner;

public class Time {
    /** 0 órától eltelt percek száma. */
    private int min;

    /** Létrehoz egy idő osztályt, megadva az órát és a percet. */
    public Time(int hour, int min){
        this.min = min;
        this.min += 60 * hour;
    }

    /** Létrehoz egy idő osztályt, hour:00 állapotban. */
    public Time(int hour){
        this.min = 60 * hour;
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
        Time newTime =  new Time(0, this.getMinsOnly()+t.getMinsOnly());
        if (newTime.getMinsOnly() >= 1440){
            throw new TimeOverflowException();
        }
        return newTime;
    }

    public Time addMins(int addMin) throws TimeOverflowException{
        int retMins = this.min;
        retMins += addMin;
        if (retMins >= 1440){
            throw new TimeOverflowException();
        }
        return new Time(0, retMins);
    }

    public Time addHours(int addH) throws TimeOverflowException{
        int retMins = min;
        retMins += addH * 60;
        if (retMins >= 1440){
            throw new TimeOverflowException();
        }
        return new Time(0, retMins);
    }

    public Time sub(Time t) throws TimeUnderflowException{
        int retMins = this.min;
        retMins -= t.getMinsOnly();
        if(retMins < 0){
            throw new TimeUnderflowException();
        }
        return new Time(0, retMins);
    }

    public Time subMins(int subMin) throws TimeUnderflowException{
        int retMins = this.min;
        retMins -= subMin;
        if(retMins < 0){
            throw new TimeUnderflowException();
        }
        return new Time(0, retMins);
    }

    public Time subHours(int subH) throws TimeUnderflowException{
        int retMins = min;
        retMins -= subH * 60;
        if(retMins < 0){
            throw new TimeUnderflowException();
        }
        return new Time(0, retMins);
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

    public int compareTo(Time t){
        int kulonb = this.getMinsOnly() - t.getMinsOnly();
        //return Integer.compare(kulonb, 0);
        if(kulonb == 0)
            return 0;
        else if(kulonb > 0)
            return 1;
        else{
            return -1;
        }
    }

    public String toString() {
        int perc = getMins();
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
        return min == time.min;
    }
}
